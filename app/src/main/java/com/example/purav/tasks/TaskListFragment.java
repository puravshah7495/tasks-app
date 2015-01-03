package com.example.purav.tasks;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Purav on 12/29/2014.
 */
public class TaskListFragment extends ListFragment {
    private ArrayAdapter<Tasks> mAarrayAdapter;
    private TasksDataSource dataSource;
    private ArrayList<Tasks> tasks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        dataSource = new TasksDataSource(inflater.getContext());
        dataSource.open();
        tasks = (ArrayList<Tasks>) dataSource.getAllTasks();
        mAarrayAdapter = new ArrayAdapter<Tasks>(inflater.getContext(),android.R.layout.simple_list_item_1,tasks);
        setListAdapter(mAarrayAdapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void newTask(Tasks t) {
        tasks.add(t);
        Log.w("msg",""+tasks.size());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        FragmentManager fragmentManager = getFragmentManager();

        Bundle args = new Bundle();
        Tasks t = (Tasks) l.getItemAtPosition(position);
        args.putLong("ID", t.getId());
        args.putString("TITLE",t.getTitle());
        args.putString("NOTES",t.getNote());

        TaskEditFragment taskEditFragment = new TaskEditFragment();
        taskEditFragment.setArguments(args);
        fragmentManager.beginTransaction().replace(R.id.fragment_container,taskEditFragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        menu.getItem(1).setIcon(android.R.drawable.ic_menu_add);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        dataSource.close();
        super.onPause();
    }
}
