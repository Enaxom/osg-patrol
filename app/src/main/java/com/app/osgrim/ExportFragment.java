package com.app.osgrim;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.osgrim.data.Bilan;
import com.app.osgrim.data.BilanCir;
import com.app.osgrim.data.BilanFonc;
import com.app.osgrim.data.BilanLes;
import com.app.osgrim.data.Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;


/**
 * ExportFragment class. Belongs to MainActivity and represents the third tab of the application.
 * The data importation and exportation are handled here. <br>
 * Classe ExportFragment. Appartient à MainActivity et représente le troisième onglet de
 * l'application. L'importation et l'exportation des données sont gérées ici.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class ExportFragment extends Fragment {

	/**
	 * The MainActivity class. Needed because it has all the lists used in the fragments. <br>
	 * La classe MainActivity. Nécessaire car elle contient toutes les listes utilisées dans les
	 * fragments.
	 */
	private MainActivity mainAct;

	/**
	 * The JSONArray which contains all the report JSONObject. It's used in two methods so it's
	 * a class variable. <br>
	 * Le JSONArray qui contient tous les rapports en JSONObject. C'est utilisé dans 2 méthodes donc c'est une variable de classe.
	 */
	private JSONArray reportArray;

	private JSONArray bilanArray;

	private JSONArray dataArray;

	private JSONObject dataObject;

	private Button btnImport, btnExport;

	/**
	 * Never called but needed for the class structure. <br>
	 * Jamais appelé mais nécessaire pour la structure de la classe.
	 */
	public ExportFragment() {
		// Required empty public constructor
	}

	/**
	 * Method executed when the application starts or when data is imported. <br>
	 * Méthode exécutée quand l'application démarre ou quand des données sont importées.
	 * @param inflater The layout used for this class. <br> Le layout utilisé dans cette classe.
	 * @param container The ViewGroup associated to the class. <br> Le ViewGroup associé à la
	 *                     classe.
	 * @param savedInstanceState Used to save the instance of the application. <br> Utilisé pour
	 *                              enregistrer l'instance de l'application.
	 * @return The View of the class exportFragment. <br> La Vue de la classe exportFragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View exportView = inflater.inflate(R.layout.fragment_export, container, false);
		mainAct = (MainActivity) getActivity();

		// Get the elements of the view
		// Récupération des éléments de la vue
		TextView tvInstructions = exportView.findViewById(R.id.tvInstructions);
		btnImport = exportView.findViewById(R.id.btnImport);
		btnExport = exportView.findViewById(R.id.btnExport);

		// Set the text of the different component
		// Fixe le texte des différents composants
		tvInstructions.setText(mainAct.messages.get("instructions"));
		btnImport.setText(mainAct.messages.get("importData"));
		btnExport.setText(mainAct.messages.get("exportData"));

		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			btnImport.setBackgroundResource(R.drawable.blue_button);
			btnExport.setBackgroundResource(R.drawable.blue_button);
		} else {
			btnImport.setBackgroundResource(R.drawable.btn_import);
			btnExport.setBackgroundResource(R.drawable.btn_export);
		}

		// Define the click listener of the import and export buttons
		// Définie l'écouteur de click des boutons d'importation et d'exportation
		btnImport.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*
				If the user already imported data, he's warned that the importation will delete
				all the reports saved on the application.
				Si l'utilisateur a déjà importé des données, il est avertil que l'importation
				supprimera tous les rapports enregistrés sur l'application.
				 */
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

	/**
	 * Exports the data of the application to a txt file containing a json object in the Download
	 * folder of the device. Fill the JSONArray with all the JSONObject of each report (get in the
	 * reports list). <br>
	 * Exporte les données de l'application dans un fichier texte contenant un objet json dans le
	 * dossier Download de l'appareil. Remplie le JSONArray avec tous les JSONObject de chaque
	 * rapport (récupération dans la liste reports).
	 */
	private void exportData() {

		/*
		Modification du 23/01
		Message indiquant qu'il n'y a pas de rapport à exporter quand la liste reports est vide

		Modification 21/04
		Thread pour popup de chargement quand on fait un export
		 */

		dataArray = new JSONArray();
		dataObject = new JSONObject();

		bilanArray = new JSONArray();

		reportArray = new JSONArray();

		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mainAct);
		dialogBuilder.setMessage(mainAct.messages.get("loading")).setCancelable(false);
		final AlertDialog dialog = dialogBuilder.create();
		dialog.show();

		@SuppressLint("HandlerLeak") final Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 1) { // success
					try {
						for (Report report : mainAct.reports)
							reportArray.put(report.getJSONReport());

						dataObject.put("reports", reportArray);



						switch (mainAct.bilanLevel) {
							case 1:
								for (Bilan bil : mainAct.bilanCirList) {
									JSONObject bilanObj = new JSONObject();
									BilanCir bilan = (BilanCir) bil;
									JSONObject obj = bilan.getJsonBilanCir();
									bilanObj.put("bilanCir", obj);
									obj = bilan.getJsonBilanFoncDef();
									bilanObj.put("bilanFonc", obj);
									obj = bilan.getJsonBilanLesDef();
									bilanObj.put("bilanLes", obj);
									bilanArray.put(bilanObj);
								}
								break;
							case 2:
								for (Bilan bil : mainAct.bilanFoncList) {
									JSONObject bilanObj = new JSONObject();
									BilanFonc bilan = (BilanFonc) bil;
									JSONObject obj = bilan.getBilanCir().getJsonBilanCir();
									bilanObj.put("bilanCir", obj);
									obj = bilan.getJsonBilanFonc();
									bilanObj.put("bilanFonc", obj);
									obj = bilan.getJsonBilanLesDef();
									bilanObj.put("bilanLes", obj);
									bilanArray.put(bilanObj);
								}
								break;
							case 3:
								for (Bilan bil : mainAct.bilanLesList) {
									JSONObject bilanObj = new JSONObject();
									BilanLes bilan = (BilanLes) bil;
									JSONObject obj = bilan.getBilanCir().getJsonBilanCir();
									bilanObj.put("bilanCir", obj);
									obj = bilan.getBilanFonc().getJsonBilanFonc();
									bilanObj.put("bilanFonc", obj);
									obj = bilan.getBilanLesJson();
									bilanObj.put("bilanLes", obj);
									bilanArray.put(bilanObj);
								}
								break;
							default:
								break;
						}
						dataObject.put("bilanLevel", mainAct.bilanLevel);
						dataObject.put("bilans", bilanArray);

					} catch (JSONException e) {
						e.printStackTrace();
					}

					// When all the reports are saved in JSON in the reportArray, the JSONArray can be saved
					// in the export file.
					// Quand tous les rapports sont enregistrés en JSON dans le reportArray, le JSONArray
					// peut être enregistré dans le fichier d'export.
					writeExportFile();
					dialog.dismiss();
				} else if (msg.what == 0) { // error
					dialog.dismiss();
					mainAct.makeAlertInfo(mainAct.messages.get("noData"));
				}
			}
		};

		new Thread() {
			public void run() {
				if (mainAct.reports.size() > 0 || mainAct.bilanCirList.size() > 0 || mainAct.bilanFoncList.size() > 0 || mainAct.bilanLesList.size() > 0) {
					handler.sendEmptyMessage(1);
				} else {
					handler.sendEmptyMessage(0);
				}
			}
		}.start();
	}

	/**
	 * Writes the export text file in the Download folder of the device. If the application
	 * doesn't have the permissions to write on the device, it asked the user to give this rights. <br>
	 * Ecrit le fichier texte d'exportation dans le dossier Download de l'appareil. Si
	 * l'application n'a pas les permissions pour écrire sur l'appareil, elle demande à
	 * l'utilisateur de donner ces droits en question.
	 */
	private void writeExportFile() {
		try {
			if (!(ContextCompat.checkSelfPermission(mainAct, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
				requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
				// If the permissions aren't given, the onRequestPermissionsResult() will be
				// called and the writeExportFile() method will be called when the permissions
				// are given.
				// Si les droits ne sont pas donnés, la méthode onRequestPermissionsResult() sera
				// appelée et writeExportFile() sera appelée quand les permissions sont données.
			} else {
				File file =
						new File(Environment.getExternalStorageDirectory() + File.separator + "Download" + File.separator + "data_export.txt");

				/*
				if (file.exists())
					file.delete();

				file.createNewFile();
				 */

				Writer output = new BufferedWriter(new FileWriter(file));
				// Write the JSON array containing all the reports in the file
				// Ecriture du JSON array qui contient tous les rapports dans le fichier
				String dataString = dataObject.toString();
				output.write(dataString);
				output.close();

				/*
				Important to scanFile when we create a file otherwise it can be hidden when the
				device is plugged to a computer. If we create a json file instead of a text file,
				the scanFile() method delete the json file created on the tablet.
				It's why we create a text file for the exportation.
				 */
				/*
				Important d'utiliser scanFile quand on créé un fichier sinon il peuyt être caché
				quand l'appareil est branché à un PC. Si on créé un fichier json à la place d'un
				fichier texte, la méthode scanFile() supprime le fichier json créé sur la
				tablette. C'est pourquoi on créé un fichier texte pour l'exportation.
				 */
				scanFile(mainAct, file);
				mainAct.makeAlertInfo(mainAct.messages.get("validExport"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Scans the file given in parameters in the device file explorer and allows to update the
	 * file explorer to make sure a plugged computer could see it. <br>
	 * Scanne le fichier donné en paramètres dans l'explorateur de fichier de l'appareil et
	 * permet de mettre à jour l'explorateur de fichier pour être sûr qu'un ordinateur branché à
	 * l'appareil puisse le voir.
	 * @param ctxt Application context. Here it'll always be the MainActivity. <br> Contexte de
	 *                l'application. Ici ce sera toujours la classe MainActivity.
	 * @param f The file to scan. <br> Le fichier à scanner.
	 */
	private void scanFile(Context ctxt, File f) {
		MediaScannerConnection.scanFile(ctxt, new String[] {f.getAbsolutePath()}, new String[] {""}, null);
	}

	/**
	 * Each time a permission'll be asked, this method will be called. It allows the app to know
	 * when the permission is accorded and to call the exportation method only if the right
	 * permission is asked. <br>
	 * Chaque fois qu'une permission va être demandée, cette méthode sera
	 * appelée. Ca permet à l'application de savoir quand la permission est accordée et d'appeler
	 * la méthode d'exportation seulement si la bonne permission est demandée.
	 * @param requestCode The requestCode. We don't use it. <br> Le code de requête. On ne s'en sert
	 *                       pas.
	 * @param permissions The array of asked permissions. <br> Le tableau de permissions demandées.
	 * @param grantResults The array of the responses where we can know if the permission is
	 *                        granted or not. <br> Le tableau de réponses où on peut savoir si la
	 *                        permission est accordée ou non.
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,
										   int[] grantResults) {
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
				writeExportFile(); // The permission WRITE_EXTERNAL_STORAGE is granted so the
		// exportation method can be called.
	}

	@Override
	public void onConfigurationChanged(@NonNull Configuration config) {
		super.onConfigurationChanged(config);
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			btnImport.setBackgroundResource(R.drawable.blue_button);
			btnExport.setBackgroundResource(R.drawable.blue_button);
		} else {
			btnImport.setBackgroundResource(R.drawable.btn_import);
			btnExport.setBackgroundResource(R.drawable.btn_export);
		}
	}
}
