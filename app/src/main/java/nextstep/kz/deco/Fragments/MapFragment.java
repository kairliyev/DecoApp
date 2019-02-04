package nextstep.kz.deco.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nextstep.kz.deco.Data.ShopData;
import nextstep.kz.deco.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener {


    private static final String TAG = MapFragment.class.getSimpleName();

    private MapView mapView;
    private ImageButton mylocation;
    private ImageButton zoom_in;
    private ImageButton zoom_out;

    private GoogleMap googleMap;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mLastLocation;
    private Marker mCurrLocationMarker;

    private BottomSheetBehavior bottomSheetBehavior;
    private LinearLayout bottom_sheet;

    private ViewGroup peek_height_layout;
    private ImageView image_shop;
    private TextView name_shop;
    private TextView type_shop;
    private TextView address_shop;

    private ViewGroup phone_call_layout;
    private TextView phone_call;
    private TextView phone_call_name;

    private TextView working_time_status;
    private TextView working_time;

    private List<ShopData> shopDataList;
    private List<Marker> shopLocationMarkers;

    private boolean loc = false;
    private boolean per_acc = false;


    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mylocation = view.findViewById(R.id.my_location);
        zoom_in = view.findViewById(R.id.zoom_in);
        zoom_out = view.findViewById(R.id.zoom_out);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);


        bottom_sheet = view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        onBottomSheet(view);

        mylocation.setOnClickListener(this);
        zoom_in.setOnClickListener(this);
        zoom_out.setOnClickListener(this);

        return view;
    }

    private void toggleBottomSheet(ShopData data) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        Log.d(TAG, data.getShop_admin_name());
        Picasso.get().load(data.getShop_image()).into(image_shop);
        name_shop.setText(data.getShop_name());
        type_shop.setText(data.getShop_type());
        address_shop.setText(data.getShop_address());
        phone_call.setText(data.getShop_admin_phone_number());
        phone_call_name.setText(data.getShop_admin_name());
        working_time.setText(data.getShop_working_time());
    }

    private void onBottomSheet(View view) {
        peek_height_layout = view.findViewById(R.id.peek_height_layout);
        image_shop = view.findViewById(R.id.shop_image);
        name_shop = view.findViewById(R.id.name_shop);
        type_shop = view.findViewById(R.id.type_shop);
        address_shop = view.findViewById(R.id.address_shop);

        phone_call_layout = view.findViewById(R.id.phone_call_layout);
        phone_call = view.findViewById(R.id.phone_call);
        phone_call_name = view.findViewById(R.id.phone_call_name);

        working_time = view.findViewById(R.id.working_time);
        working_time_status = view.findViewById(R.id.working_time_status);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.d(TAG, "BottomSheet hidden");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.d(TAG, "BottomSheet expanded");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.d(TAG, "BottomSheet collapsed");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.d(TAG, "BottomSheet dragging");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.d(TAG, "BottomSheet settling");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        bottom_sheet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_location:
                final LocationManager manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (per_acc && !statusOfGPS) {
                    Toast.makeText(getContext(), "Включите GPS!", Toast.LENGTH_SHORT).show();
                } else if (per_acc && statusOfGPS) {
                    buildGoogleApiClient();
                }
                break;
            case R.id.zoom_in:
                googleMap.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case R.id.zoom_out:
                googleMap.animateCamera(CameraUpdateFactory.zoomOut());
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        /*mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);*/
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        mLastLocation = new LatLng(location.getLatitude(), location.getLongitude());

        onAddMarkersFromData();

        if (loc) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLastLocation, googleMap.getCameraPosition().zoom));
        } else {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLastLocation, 13));
        }
        loc = true;

    }

    private void onAddMarkersFromData() {
        shopDataList = new ArrayList<>();
        shopDataList.add(new ShopData("http://toplogos.ru/images/logo-decathlon.jpg", "Decathlon Shop", "43.242415,76.941972", "Abay avenue, 45", "Network of sports shops", "https://www.decathlon.co.uk/store//en/0070104001040/photo-mag1145914406.jpg", "Olzhas Adambay", "+7 (727) 255 52 52", "On workdays 10:00 - 18:00, lunch hour 14:00 - 14:40\n Saturday 10:00 - 15:30, lunch hour 13:00 - 13:40\n Sunday output"));
        shopDataList.add(new ShopData("http://toplogos.ru/images/logo-decathlon.jpg", "Decathlon Fitness centre", "43.233974,76.886492", "Satpayev street, 78", "Network of fitness centre", "http://fitline-sport.ru/media/clubs/2018/1/24/16195832_759497857546397_551039083875516988_n.jpg", "Evgeniy Ivanov", "+7 (727) 451 40 40", "On workdays 09:00 - 18:00, lunch hour 13:00 - 13:40\n Saturday 10:00 - 15:30, lunch hour 13:00 - 13:40\n Sunday output"));
        shopDataList.add(new ShopData("http://toplogos.ru/images/logo-decathlon.jpg", "Decathlon Shop", "43.214555,76.893420", "Roziybakiyev street, 270", "Network of sports shops", "https://www.decathlon.co.uk/store//en/0070084700847/photo-mag1950395172.JPG", "Anna Petrova", "+7 (727) 256 23 24", "On workdays 10:00 - 18:00, lunch hour 14:00 - 14:40\n Saturday 10:00 - 15:30, lunch hour 13:00 - 13:40\n Sunday output"));
        shopDataList.add(new ShopData("http://toplogos.ru/images/logo-decathlon.jpg", "Decathlon Fitness centre", "43.227074,76.918014", "Timiryazev street, 41", "Network of fitness centre", "http://fitline-sport.ru/media/clubs/2018/1/24/22688378_912061425623372_6105922130042741576_n.jpg", "Aygerim Ospanova", "+7 (727) 375 42 42", "On workdays 10:00 - 18:00, lunch hour 14:00 - 14:40\n Saturday 10:00 - 15:30, lunch hour 13:00 - 13:40\n Sunday output"));
        shopDataList.add(new ShopData("http://toplogos.ru/images/logo-decathlon.jpg", "Decathlon Shop", "43.195700,76.875861", "Al-Farabi avenue, 19", "Network of sports shops", "http://1.bp.blogspot.com/-CGf5yiEskTI/VJNRMoqRdbI/AAAAAAAABbE/mWl-x60HLxU/s1600/IMG_0284.JPG", "Erzhan Aliyev", "+7 (727) 425 63 63", "On workdays 10:00 - 18:00, lunch hour 14:00 - 14:40\n Saturday 10:00 - 15:30, lunch hour 13:00 - 13:40\n Sunday output"));

        shopLocationMarkers = new ArrayList<>();

        for (int i = 0; i < shopDataList.size(); i++) {
            createMarker(shopDataList.get(i));
        }

    }

    protected Marker createMarker(ShopData data) {
        String[] latlng  = data.getLatlng().split(",");
        double lat = Double.parseDouble(latlng[0]);
        double lng = Double.parseDouble(latlng[1]);
        mCurrLocationMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)));;
        shopLocationMarkers.add(mCurrLocationMarker);
        return mCurrLocationMarker;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();


                        }
                        googleMap.setMyLocationEnabled(true);
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public boolean onMarkerClick(Marker marker) { ;
        for (int i = 0; i < shopDataList.size(); i++) {
            if (marker.equals(shopLocationMarkers.get(i))) {
                toggleBottomSheet(shopDataList.get(i));
                return true;
            }
        }

        return false;
    }


    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                per_acc = true;
                buildGoogleApiClient();
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
}
