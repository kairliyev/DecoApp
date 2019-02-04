package nextstep.kz.deco.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import nextstep.kz.deco.R;

public class EventItemActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_item);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String name = extras.getString("e_name");
        String date = extras.getString("e_date");
        String price = extras.getString("e_price");
        String des = extras.getString("e_des");
        String site = extras.getString("e_site");
        String city = extras.getString("e_city");
        String image = extras.getString("e_image");

        ImageView iv = findViewById(R.id.event_item_title_iv);
        TextView title = findViewById(R.id.event_item_title_tv);
        TextView desc = findViewById(R.id.event_item_des_tv);
        TextView pr = findViewById(R.id.price_tv);
        TextView dat = findViewById(R.id.date_tv);
        TextView cit = findViewById(R.id.city_tv);
        TextView sit = findViewById(R.id.site_tv);

        Picasso.get().load(image).into(iv);
        title.setText(name);
        desc.setText(des);
        pr.setText(price);
        dat.setText(date);
        cit.setText(city);
        sit.setText(site);
    }
}
