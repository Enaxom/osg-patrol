package com.app.osgrim;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.osgrim.data.Report;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExportFragment extends Fragment {

	private View exportView;
	private MainActivity mainAct;
	private JSONArray reportArray;

	public ExportFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		exportView = inflater.inflate(R.layout.fragment_export, container, false);
		mainAct = (MainActivity) getActivity();

		TextView tvInstructions = exportView.findViewById(R.id.tvInstructions);
		Button btnImport = exportView.findViewById(R.id.btnImport);
		Button btnExport = exportView.findViewById(R.id.btnExport);

		tvInstructions.setText(mainAct.messages.get("instructions"));
		btnImport.setText(mainAct.messages.get("importData"));
		btnExport.setText(mainAct.messages.get("exportData"));

		btnImport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!mainAct.isFirstTime) {
					AlertDialog alertDialog = new AlertDialog.Builder(mainAct).create();
					alertDialog.setMessage(mainAct.messages.get("warningImport"));

					alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mainAct.messages.get("yes"),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									mainAct.importData();
								}
							});

					alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, mainAct.messages.get("no"),
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									dialogInterface.dismiss();
								}
							});

					alertDialog.show();

					TextView messageView = alertDialog.findViewById(android.R.id.message);
					messageView.setGravity(Gravity.CENTER);

					Button btnPositive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
					Button btnNegative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

					LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
					layoutParams.weight = 10;
					btnPositive.setLayoutParams(layoutParams);
					btnNegative.setLayoutParams(layoutParams);
				} else {
					mainAct.importData();
				}
			}
		});

		btnExport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				exportData();
			}
		});

		return exportView;
	}

	private void exportData() {
		reportArray = new JSONArray();

		try {
			for (Report report : mainAct.reports)
				reportArray.put(report.getJSONReport());
		} catch (JSONException e) {
			e.printStackTrace();
		}

		writeExportFile();
	}

	private void writeExportFile() {
		try {
			if (!(ContextCompat.checkSelfPermission(mainAct, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
			} else {
				// File folder = new File(Environment.getExternalStorageDirectory() + File
				// .separator + "Osgrim");
				File file =
						new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "data_export.txt");

				Writer output = new BufferedWriter(new FileWriter(file));
				output.write(reportArray.toString());
				output.close();

				scanFile(mainAct, file);
				// Toast.makeText(mainAct, "Export to " + file.getAbsolutePath(), Toast
				// .LENGTH_LONG).show();
				mainAct.makeAlertInfo(mainAct.messages.get("validExport"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void scanFile(Context ctxt, File f) {
		MediaScannerConnection.scanFile(ctxt, new String[] {f.getAbsolutePath()}, new String[] {""}, null);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
				writeExportFile();
	}
}
