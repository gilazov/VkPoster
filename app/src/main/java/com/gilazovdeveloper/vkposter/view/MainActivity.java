package com.gilazovdeveloper.vkposter.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gilazovdeveloper.vkposter.LoginActivity;
import com.gilazovdeveloper.vkposter.R;
import com.gilazovdeveloper.vkposter.model.vo.Post;

public class MainActivity extends AppCompatActivity {
    private static final String CHANGE_USER = "change_user";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startPostListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean(CHANGE_USER, true);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startPostListFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, PostListFragment.newInstance());
        transaction.commit();
    }

    public void startPostPreviewFragment(Post post){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer, PostPreviewFragment.newInstance(post));
        transaction.addToBackStack(null);
        transaction.commit();

    }

}
