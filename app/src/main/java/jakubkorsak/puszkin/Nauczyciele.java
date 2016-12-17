package jakubkorsak.puszkin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Nauczyciele extends AppCompatActivity {

    private ListView list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nauczyciele);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setTitleTextColor(Color.WHITE);

        list = (ListView) findViewById(R.id.listView1);

        String teachers[] = {
                "Ewa Andrzejewska-Sidorowicz",
                "Marta Bagińska",
                "Mariusz Biniewski",
                "Tomasz Bobin",
                "Agnieszka Bujak",
                "Monika Duchnowska",
                "Radosław Duchnowski",
                "Piotr Pluta",
                "Dariusz Gerek",
                "Aleksandra Gratka",
                "Olga Grochowska",
                "Zofia Gromala",
                "Krystyna Gutowska",
                "Małgorzata Jach",
                "Justyna Kaczyńska - Bronisz",
                "Anna Kociołek",
                "Piotr Kociołek",
                "Bartłomiej Korasiak",
                "Anna Korytowska",
                "Teresa Kostyszak",
                "Zbigniew Łuczka",
                "Kamil Maciejewski",
                "Robert Malenkowski",
                "Barbara Marjowska",
                "Elżbieta Mierzejewska",
                "Sylwia Miler",
                "Elżbieta Niekrasz",
                "Małgorzata Niewiadomska",
                "Ewa Olczak",
                "Ireneusz Ostapczuk",
                "Monika Ostrowska",
                "Agata Piekarska",
                "Zbigniew Pietrzak",
                "Iryna Polikowska",
                "Marek Przeworski",
                "Iwona Różańska",
                "Gabriela Sajkowska",
                "Agata Stapf - Skiba",
                "Paweł Szafrański",
                "Ewa Szmit",
                "Jolanta Śmigiel",
                "Justyna Walus",
                "Arkadiusz Wiącek",
                "Małgorzata Wiczkowska",
                "Jolanta Winnik",
                "Paweł Wiśniewski",
                "Paulina Wysocka",
                "Paweł Zaborowski"
        };
        ArrayList<String> carL = new ArrayList<>();
        carL.addAll( Arrays.asList(teachers) );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, carL);
        list.setAdapter(adapter);
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Object sender= list.getItemAtPosition(position);
                String str = getID((String)sender);

                Toast.makeText(getApplicationContext(),"Wybrano: "+str,Toast.LENGTH_SHORT).show();
                /**
                * tutaj przechwytuję Sender i wpisuję go do String
                * */

                Intent intent = new Intent(Nauczyciele.this,InnePlany.class);
                intent.putExtra("nauczyciel",str);
                startActivity(intent);
            }
        });
    }
    private String getID(String h){

        assert h != null;
        switch(h){

            case "Ewa Andrzejewska-Sidorowicz":
                h = "n1";
                break;
            case "Marta Bagińska":
                h = "n2";
                break;
            case "Mariusz Biniewski":
                h = "n3";
                break;
            case "Tomasz Bobin":
                h = "n4";
                break;
            case "Agnieszka Bujak":
                h = "n5";
                break;
            case "Monika Duchnowska":
                h = "n6";
                break;
            case "Radosław Duchnowski":
                h = "n7";
                break;
            case "Piotr Pluta":
                h = "n8";
                break;
            case "Dariusz Gerek":
                h = "n9";
                break;
            case "Aleksandra Gratka":
                h = "n10";
                break;
            case "Olga Grochowska":
                h = "n11";
                break;
            case "Zofia Gromala":
                h = "n12";
                break;
            case "Krystyna Gutowska":
                h = "n13";
                break;
            case "Małgorzata Jach":
                h = "n14";
                break;
            case "Justyna Kaczyńska - Bronisz":
                h = "n15";
                break;
            case "Anna Kociołek":
                h = "n16";
                break;
            case "Piotr Kociołek":
                h = "n17";
                break;
            case "Bartłomiej Korasiak":
                h = "n18";
                break;
            case "Anna Korytowska":
                h = "n19";
                break;
            case "Teresa Kostyszak":
                h = "n20";
                break;
            case "Zbigniew Łuczka":
                h = "n21";
                break;
            case "Kamil Maciejewski":
                h = "n22";
                break;
            case "Robert Malenkowski":
                h = "n23";
                break;
            case "Barbara Marjowska":
                h = "n24";
                break;
            case "Elżbieta Mierzejewska":
                h = "n25";
                break;
            case "Sylwia Miler":
                h = "n26";
                break;
            case "Elżbieta Niekrasz":
                h = "n27";
                break;
            case "Małgorzata Niewiadomska":
                h = "n28";
                break;
            case "Ewa Olczak":
                h = "n29";
                break;
            case "Ireneusz Ostapczuk":
                h = "n30";
                break;
            case "Monika Ostrowska":
                h = "n31";
                break;
            case "Zbigniew Pietrzak":
                h = "n32";
                break;
            case "Julia Pławska-Olejniczak":
                h = "n33";
                break;
            case "Iryna Pokowska":
                h = "n34";
                break;
            case "Marek Przeworski":
                h = "n35";
                break;
            case "Iwona Różańska":
                h = "n36";
                break;
            case "Gabriela Sajkowska":
                h = "n37";
                break;
            case "Agata Stapf - Skiba":
                h = "n38";
                break;
            case "Paweł Szafrański":
                h = "n39";
                break;
            case "Ewa Szmit":
                h = "n40";
                break;
            case "Jolanta Śmigiel":
                h = "n41";
                break;
            case "Justyna Walus":
                h = "n42";
                break;
            case "Arkadiusz Wiącek":
                h = "n43";
                break;
            case "Małgorzata Wiczkowska":
                h = "n44";
                break;
            case "Jolanta Winnik":
                h = "n45";
                break;
            case "Paweł Wiśniewski":
                h = "n46";
                break;
            case "Paulina Wysocka":
                h = "n47";
                break;
            case "Paweł Zaborowski":
                h = "n48";
                break;

        }
        return h;
    }
}
