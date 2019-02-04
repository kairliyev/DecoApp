package nextstep.kz.deco.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nextstep.kz.deco.R;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        final EditText loc = findViewById(R.id.editloc);
        final EditText time = findViewById(R.id.edittime);
        final EditText type = findViewById(R.id.edittype);
        final EditText status = findViewById(R.id.editstatus);

        Button button = findViewById(R.id.send_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}
