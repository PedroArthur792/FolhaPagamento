package br.ulbra.folhapagamento;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

import br.ulbra.folhapagamento.R;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btCal;
        EditText edSalario, edFilho, edNome;
        TextView txRes;
        RadioGroup rgSex;

        btCal = findViewById(R.id.btCal);
        edSalario = findViewById(R.id.edtSalario);
        edNome = findViewById(R.id.edtNome);
        edFilho = findViewById(R.id.edtFilho);
        rgSex = findViewById(R.id.rgSexo);
        txRes = findViewById(R.id.txtRes);

        btCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                double salLiq, descINSS = 0, descIR = 0, salFam = 0;
                double salario = Double.parseDouble(edSalario.getText().toString());
                int filho = Integer.parseInt(edFilho.getText().toString());
                String nome = edNome.getText().toString().trim();

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Aviso");
                dialogo.setMessage("Selecione um sexo");

                if(rgSex.getCheckedRadioButtonId() == -1){
                    dialogo.show();
                    return;
                }
                if(salario == 0){
                    dialogo.setMessage("Por favor coloque um valor válido no campo sálario");
                    dialogo.show();
                    return;
                }
                if(nome.isEmpty()){
                    dialogo.setMessage("Por favor coloque um nome válido");
                    dialogo.show();
                    return;
                }

                if (salario <= 1212.00){
                    descINSS =  salario * 0.075;
                    salFam = filho*56.47;
                } else if (salario <= 2427.35) {
                    descINSS =  salario * 0.09;
                } else if(salario <= 3641.03){
                    descINSS =  salario * 0.12;
                } else{
                    descINSS =  salario * 0.014;
                }

                if (salario < 1903.99) {
                    descIR = 0;
                } else if (salario <= 2862.65) {
                    descIR = (salario * 0.075);
                } else if (salario <= 3751.05) {
                    descIR = (salario * 0.15);
                } else if (salario <= 4664.68) {
                    descIR = (salario * 0.225) ;
                } else {
                    descIR = (salario * 0.275);
                }

                salLiq = salario - (descINSS+descIR)+salFam;
                DecimalFormat df = new DecimalFormat("0.00");

                if(rgSex.getCheckedRadioButtonId()== R.id.rbMasc){
                    txRes.setText("Sr. " +nome+" estes são seus descontos:"
                            +"\nSálario Bruto: R$"+salario
                            +"\nDesconto do INSS: R$ "+df.format(descINSS)+
                            "\nDesconto do IR: R$ "+df.format(descIR)+
                            "\n Sálario Fámilia: R$ "+df.format(salFam)+
                            "\nSalário Liquído: R$ "+df.format(salLiq));
                } else {
                    txRes.setText("Sra. " +nome+" estes são seus descontos:"
                            +"\nSálario Bruto: R$"+salario
                            +"\nDesconto do INSS: R$ "+df.format(descINSS)+
                            "\nDesconto do IR: R$ "+df.format(descIR)+
                            "\n Sálario Fámilia: R$ "+df.format(salFam)+
                            "\nSalário Liquído: R$ "+df.format(salLiq));
                }

            }
        });

    }
}