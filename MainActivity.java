package com.example.aniket.guesstheword;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static int MIN_WORD_LENGTH = 4;
    private ArrayList<String> words;
    static String x;
    String UnderScore = "";
    char [] copy = new char[15];
    char [] underscore = new char[15];
    int click=0;
    public void start(View view ){
        x = getAnyWord();
        String w = "";
        int len = x.length();
        Toast toast = Toast.makeText(this, "The word has "+ len + " letters ", Toast.LENGTH_SHORT);
        toast.show();
        for (int i = 0;i<len;i++)
        {
            w +="-";
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(w);

        toast = Toast.makeText(this,"Guess a letter ",Toast.LENGTH_SHORT);
        toast.show();
        for (int i = 0;i<len;i++)
        {
            copy[i] = x.charAt(i);
            underscore[i]='-';
        }
        Button click = (Button)findViewById(R.id.button);
        click.setEnabled(true);
        click.setClickable(true);

    }


    public void clickFunction(View view ){
        int len = x.length();
        click++;
        int left = len+2-click;
        TextView leftText = (TextView)findViewById(R.id.textView6);
        leftText.setText("Left : "+ left);
        if (click==len+2)
        {
            Toast toast = Toast.makeText(this, "You lose! :( The word was : "+x,Toast.LENGTH_LONG);
            toast.show();
            click = 0;
        }
        TextView textView = (TextView)findViewById(R.id.textView);
        char guess;
        int k = 0;
        int wrong = 0;
        int i=0;
        int j;
        for (j=0;j<len+1;j++)
        {
            EditText editText = (EditText)findViewById(R.id.editText);
            guess = editText.getText().charAt(0);

            for ( i = 0;i<len;i++)
            {
                if (copy[i] == guess) {
                    while (copy[i] == guess) {
                        underscore[i] = copy[i];
                        copy[i] = '-';
                        textView.setText(underscore, 0, len);
                    }
                }
            }
            int count=0;
            for (k = 0;k<len;k++)
            {
                if (underscore[k] == x.charAt(k)) {
                    count++;
                }
            }
            if (count == len) {
                Toast toast = Toast.makeText(this, "You win ! :) ", Toast.LENGTH_SHORT);
                toast.show();
                click = 0;
                return;
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            wordGenerator(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }


    }

    void wordGenerator(InputStream inputStream) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        words = new ArrayList<>();
        String line = null;
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
                words.add(line.trim());
        }
        String anyWord = getAnyWord();

    }


    public String getAnyWord() {

            Random random = new Random();
            int r = random.nextInt(10000);
            String randomString = words.get(r);
            return randomString;
    }
}
