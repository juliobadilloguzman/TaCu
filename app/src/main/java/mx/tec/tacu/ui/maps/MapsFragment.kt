package mx.tec.tacu.ui.maps

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import mx.tec.tacu.PerfilTaqueria

import mx.tec.tacu.R
import mx.tec.tacu.model.GoogleMapDTO
import mx.tec.tacu.model.Taqueria
import java.lang.Exception

class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener {

    lateinit var locationManager: LocationManager

    override fun onLocationChanged(location: Location?) {
        latitude = location!!.latitude
        longitude = location.longitude

        /*Log.e("Latidud: ", latitude.toString())
        Log.e("Longitud :", longitude.toString())*/


        myPosition = LatLng(location.latitude, location.longitude)
        //println("MY POSITION: " + myPosition)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15F))

    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onProviderEnabled(provider: String?) {

    }

    override fun onProviderDisabled(provider: String?) {

    }

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mDatabase2: FirebaseFirestore

    var tmpRealTimeMarkers = ArrayList<Marker>()
    var realTimeMarkers = ArrayList<Marker>()

    var id: String = ""
    var calif: Double = 0.0
    var descripcion: String = ""
    var horario: String = ""
    var imagen: String = ""
    var nombre: String = ""
    var telefono: String = ""

    //Dimensiones del icono del marker en el mapa taquerias
    private var height = 130
    private var width = 130

    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var myPosition: LatLng = LatLng(0.0,0.0)

    val from = LatLng(18.93547,-99.21)

    val taqueriaHard = LatLng(18.93,-99.20365)

    companion object {
        var mapFragment : SupportMapFragment?=null
        val TAG: String = MapFragment::class.java.simpleName
        fun newInstance() = MapFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        locationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val root = inflater.inflate(R.layout.fragment_maps, container, false)

        permisos()

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        mDatabase2 = FirebaseFirestore.getInstance()


        val URL = getURL(myPosition,taqueriaHard)
        GetDirection(URL).execute()

        println("MY POSITION: " + URL)

        Log.e("LINK",URL)

        //currentPosition()




        return root
    }


    override fun onMapReady(googleMap: GoogleMap) {


        Log.e("MENSAJE", "SI ENTRE")

       mMap = googleMap
       mMap.isMyLocationEnabled = true



        //

        val probando =  mDatabase2.collection("TAQUERIA")



        probando.addSnapshotListener { snapshots, e ->

            probando.get().addOnSuccessListener { it ->

                it.forEach {

                    for (i in realTimeMarkers) {
                        i.remove()
                    }

                    val post = it.toObject(Taqueria::class.java)


                    val latitud: Double = post.latitud
                    val longitud: Double = post.longitud
                    val name = post.nombre
                    /*

                    Log.e("RESULTADO", post.toString())
                    calif = post.calificacion
                    descripcion = post.descripcion
                    horario = post.horario
                    imagen = post.imagen

                    telefono = post.telefono

                     */


                    //val marker1 = MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(LatLng(latitud,longitud))
                    //val marker2 = mMap.addMarker(marker1)


                    //esto lo comente
                    mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
                        override fun onMarkerClick(p0: Marker): Boolean {



                            /*

                            val intent = Intent(activity, PerfilTaqueria::class.java)
                            intent.putExtra("myCalif", calif.toString())
                            intent.putExtra("myDescripcion", descripcion)
                            intent.putExtra("myHorario", horario)
                            intent.putExtra("myImagen", imagen)
                            intent.putExtra("myNombre", nombre)
                            intent.putExtra("myTelefono", telefono)
                            startActivity(intent)

                            Log.e("CALIFICACION ", calif.toString())
                            Log.e("DESCRIPCION ", descripcion)
                            Log.e("HORARIO ", horario)
                            Log.e("IMAGEN ", imagen)
                            Log.e("NOMBRE ", nombre)
                            Log.e("TELEFONO ", telefono)

                             */

                            return false
                        }

                    })
/*
                    mMap.setOnMarkerClickListener {
                        onMarkerClick(marker2)
                    }
                    */


                    val myMarkerOptions = MarkerOptions().title(name).icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(LatLng(latitud,longitud))
                    mMap.addMarker(myMarkerOptions)


                    //tmpRealTimeMarkers.add(mMap.addMarker(myMarkerOptions))
                    Log.e("MI LISTA ES:",tmpRealTimeMarkers.toString())


                }

                realTimeMarkers.clear()
                realTimeMarkers.addAll(tmpRealTimeMarkers)

            }

        }
    }


    inner class GetDirection(val url: String): AsyncTask<Void,Void,List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body().string()
            val result = ArrayList<List<LatLng>>()

            try {
                val respObj = Gson().fromJson(data,GoogleMapDTO::class.java)
                val path = ArrayList<LatLng>()

                for(i in 0..(respObj.routes[0].legs[0].steps.size-1)){
                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble(),
                        respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())

                    path.add(startLatLng)

                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble(),
                        respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())

                    path.add(endLatLng)
                }

                result.add(path)

            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }

        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }

    }

    private fun getURL(from : LatLng, to : LatLng) : String {
        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val params = "$origin&$dest&$sensor"

        return "https://maps.googleapis.com/maps/api/directions/json?$params&key=AIzaSyBPe_X-wSmQCQWGSLWh0Okny2eEObxK-LQ"
    }

    private fun permisos() {

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //Si el permiso no se concedió, explicar porque se ocupa
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!,Manifest.permission.ACCESS_FINE_LOCATION)){

                //Muestra la explicación de manera asíncrona
                val builder = AlertDialog.Builder(context!!)
                builder.setMessage("El permiso de localización es necesario.").setTitle("Permiso requerido")
                builder.setPositiveButton("OK") {
                        dialog, id ->ActivityCompat.requestPermissions(activity!!,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10)
                }

                val dialog = builder.create()
                dialog.show()

            }else{
                //No se necesita explicación, solicitar el permiso
                ActivityCompat.requestPermissions(activity!!,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10)
                // 10 es una constante para saber que se pidió el permiso de localización
            }


        }else { //El permiso se concedió, se pide la localización
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        }

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){

            10 -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    //El permiso no se concedió, cierra la aplicación?
                }else{
                    //El permiso se concedió, se pide la localización
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
                }

            }

        }
    }



    fun iconSize(): Bitmap? {
        val bitmapdraw = resources.getDrawable(R.drawable.taco) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)

        return smallMarker
    }

}


