package mx.tec.tacu.ui.maps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import mx.tec.tacu.PerfilTaqueria

import mx.tec.tacu.R
import mx.tec.tacu.model.Taqueria

class MapsFragment : Fragment(), OnMapReadyCallback, LocationListener {

    override fun onLocationChanged(location: Location?) {
        latitude = location!!.latitude
        longitude = location.longitude

        myPosition = LatLng(location.latitude, location.longitude)
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
    private lateinit var mDatabase: DatabaseReference

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
    lateinit var myPosition: LatLng

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

        val root = inflater.inflate(R.layout.fragment_maps, container, false)

        permisos()

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        mDatabase2 = FirebaseFirestore.getInstance()
        mDatabase= FirebaseDatabase.getInstance().reference

        //currentPosition()

        return root
    }


    override fun onMapReady(googleMap: GoogleMap) {

        Log.e("MENSAJE", "SI ENTRE")

        mMap = googleMap
        mMap.isMyLocationEnabled = true

        val probando =  mDatabase2.collection("TAQUERIA")

        val vael: ValueEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val feeds = dataSnapshot.getValue(Taqueria::class.java)

                val intent = Intent(activity, PerfilTaqueria::class.java)
                intent.putExtra("myCalif", feeds?.calificacion)
                intent.putExtra("myDescripcion", feeds?.descripcion)
                intent.putExtra("myHorario", feeds?.horario)
                intent.putExtra("myImagen", feeds?.imagen)
                intent.putExtra("myNombre", feeds?.nombre)
                intent.putExtra("myTelefono", feeds?.telefono)
                startActivity(intent)

            }
        }

        probando.addSnapshotListener { snapshots, e ->

            probando.get().addOnSuccessListener { it ->

                it.forEach {

                    for (i in realTimeMarkers) {
                        i.remove()
                    }


                    val post = it.toObject(Taqueria::class.java)


                    Log.e("RESULTADO", post.toString())

                    val latitud: Double = post.latitud
                    val longitud: Double = post.longitud
                    /*
                    id = post.id.toString()
                    calif = post.calificacion
                    descripcion = post.descripcion
                    horario = post.horario
                    imagen = post.imagen
                    nombre = post.nombre
                    telefono = post.telefono

                     */

                    //val marker1 = MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(LatLng(latitud,longitud))
                    //val marker2 = mMap.addMarker(marker1)

                    mMap.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener{
                        override fun onMarkerClick(p0: Marker): Boolean {

                            mDatabase.child(p0.title).addValueEventListener(vael)
                            //probando.child(p0.title).addValueEventListener(vael)

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


                    val myMarkerOptions = MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(iconSize())).position(LatLng(latitud,longitud))
                    //mMap.addMarker(myMarkerOptions)


                    tmpRealTimeMarkers.add(mMap.addMarker(myMarkerOptions))
                    Log.e("MI LISTA ES:",tmpRealTimeMarkers.toString())


                }

                realTimeMarkers.clear()
                realTimeMarkers.addAll(tmpRealTimeMarkers)

            }
        }
    }

    private fun permisos() {

        if (ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION), 1)

            return

        }

    }



    fun iconSize(): Bitmap? {
        val bitmapdraw = resources.getDrawable(R.drawable.taco) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false)

        return smallMarker
    }

}


