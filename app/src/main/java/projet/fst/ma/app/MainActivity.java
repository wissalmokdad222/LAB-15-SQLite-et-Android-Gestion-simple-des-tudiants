package projet.fst.ma.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import projet.fst.ma.app.classes.Etudiant;
import projet.fst.ma.app.service.EtudiantService;

public class MainActivity extends AppCompatActivity {

    private EditText nom;
    private EditText prenom;
    private Button add;
    private EditText id;
    private Button rechercher;
    private Button supprimer;
    private TextView res;

    void clear() {
        nom.setText("");
        prenom.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EtudiantService es = new EtudiantService(this);

        nom = (EditText) findViewById(R.id.nom);
        prenom = (EditText) findViewById(R.id.prenom);
        add = (Button) findViewById(R.id.bn);
        id = (EditText) findViewById(R.id.id);
        rechercher = (Button) findViewById(R.id.load);
        supprimer = (Button) findViewById(R.id.delete);
        res = (TextView) findViewById(R.id.res);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = nom.getText().toString();
                String p = prenom.getText().toString();

                if (n.isEmpty() || p.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }

                es.create(new Etudiant(n, p));
                clear();
                Toast.makeText(MainActivity.this, "Étudiant ajouté", Toast.LENGTH_SHORT).show();
            }
        });

        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    res.setText("");
                    Toast.makeText(MainActivity.this, "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Etudiant e = es.findById(Integer.parseInt(txt));
                    if (e == null) {
                        res.setText("");
                        Toast.makeText(MainActivity.this, "Étudiant introuvable", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    res.setText(e.getNom() + " " + e.getPrenom());
                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "ID invalide", Toast.LENGTH_SHORT).show();
                }
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Etudiant e = es.findById(Integer.parseInt(txt));
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "Aucun étudiant à supprimer", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    es.delete(e);
                    res.setText("");
                    Toast.makeText(MainActivity.this, "Étudiant supprimé", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException ex) {
                    Toast.makeText(MainActivity.this, "ID invalide", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
