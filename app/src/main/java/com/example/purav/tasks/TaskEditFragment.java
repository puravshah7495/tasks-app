package com.example.purav.tasks;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TaskEditFragment extends Fragment {
    TasksDataSource dataSource;
    Tasks tasks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_task,container,false);
        dataSource = new TasksDataSource(container.getContext());
        dataSource.open();
        Bundle args = getArguments();

        String taskTitle = args.getString("TITLE");
        String taskNotes = args.getString("NOTES");
        Long taskId = args.getLong("ID");

         tasks = new Tasks(taskNotes,taskTitle,taskId);

        Button submit = (Button) view.findViewById(R.id.submit_button);
        EditText title = (EditText) view.findViewById(R.id.editText);
        EditText notes = (EditText) view.findViewById(R.id.note_title);
        TextView titleView = (TextView) view.findViewById(R.id.textView);

        titleView.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        title.setText(taskTitle);
        title.setEnabled(false);
        notes.setText(taskNotes);
        notes.setEnabled(false);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_edit_menu,menu);
        menu.getItem(0).setIcon(android.R.drawable.ic_menu_edit);
        menu.getItem(1).setIcon(android.R.drawable.ic_menu_delete);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_task) {
            dataSource.deleteTask(tasks);
            dataSource.close();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,new TaskListFragment()).commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    public void onResume() {
        dataSource.open();
        super.onResume();
    }
}
