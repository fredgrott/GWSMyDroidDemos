
Usage
=====

Your font files in assets folder, no subfolders as classes assume font file is at root of assets folder.

In the application class:

```java

public class OurApp extends Application {
     @Override
     public void onCreate() {
         super.onCreate();

         TypefaceHelper.initialize(this);
     }

     @Override
     public void onTerminate() {
        TypefaceHelper.destroy();
        super.onTerminate();
     }
}

```

```java

public class ourActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        TextView goodday = (textview) findViewById(r.id.goodday);
        TypefaceHelper.getInstance().setTypeface(goodday, "font_file.ttf");
    }
}

```

a group of views:

```java

     LinearLayout container = (linearLayout)findViewById(r.id.text_container);
     TypefaceHelper.getInstance().setTypeface(container, "font_file.ttf");

```


all views in an activity:
```java

      setContentView(TypefaceHelper.getInstance().setTypeface(this, R.layout.activity, "font_file.ttf")):

```

applying to a whole window:

```java

       setContentView(R.layout.activity);
       TypefaceHelper.getInstance().setTypeface(this, "font_file.ttf");

```

And to pass the font name as a string resource id is:

```java

public class OurActivity extends AppCompatActivity{

     @Override
     public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(r.layout.activity);
         TypefaceHelper.getInstance().setTypeface(this, R.string.font_primary);

```

in your res/values strings.xml file you will have:

```
     <string name="font_primary">somefontname.tff</string>

```




Credits
=======

[TypefaceHelper](https://github.com/Drivemode/TypefaceHelper)
