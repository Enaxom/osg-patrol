package com.app.osgrim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.osgrim.data.Building;
import com.app.osgrim.data.Canton;
import com.app.osgrim.data.Detail;
import com.app.osgrim.data.Frequency;
import com.app.osgrim.data.Incident;
import com.app.osgrim.data.Level;
import com.app.osgrim.data.Material;
import com.app.osgrim.data.Nature;
import com.app.osgrim.data.Report;
import com.app.osgrim.data.Service;
import com.app.osgrim.data.ServiceCat;
import com.app.osgrim.data.Space;
import com.app.osgrim.data.SpaceCat;
import com.app.osgrim.data.Team;
import com.app.osgrim.data.User;
import com.app.osgrim.data.Zone;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The MainActivity class is the first class being executed. It's here that we declare and fill all
 * the lists during the importation. It also handles the save when the user quit the application and
 * restores the data when he comes back. The three tabs are defined here. <br>
 * La classe MainActivity est la première classe à être exécutée. C'est ici que sont déclarées et
 * remplies toutes les listes durant l'importation. Elle gère aussi la sauvegarde quand
 * l'utilisateur quitte l'application et restaure les données quand il revient. Les trois onglets
 * sont définis ici.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

	/**
	 * The teams list. <br>
	 * La liste des équipes.
	 */
	protected List<Team> teams;

	/**
	 * The users list. <br>
	 * La liste des utilisateurs (rédacteurs).
	 */
	protected List<User> users;

	/**
	 * The zones list. <br>
	 * La liste des zones.
	 */
	protected List<Zone> zones;

	/**
	 * The buildings list. <br>
	 * La liste des bâtiments.
	 */
	protected List<Building> buildings;

	/**
	 * The levels list. <br>
	 * La liste des niveaux.
	 */
	protected List<Level> levels;

	/**
	 * The cantons list. <br>
	 * La liste des cantons.
	 */
	protected List<Canton> cantons;

	/**
	 * The space categories list. <br>
	 * La liste des catégories de local.
	 */
	protected List<SpaceCat> spaceCats;

	/**
	 * The spaces list. <br>
	 * La liste des objets local.
	 */
	protected List<Space> spaces;

	/**
	 * The natures list. <br>
	 * La liste des natures d'action.
	 */
	protected List<Nature> natures;

	/**
	 * The frequencies list. <br>
	 * La liste des périodicités.
	 */
	protected List<Frequency> frequencies;

	/**
	 * The materials list. <br>
	 * La liste des matériels.
	 */
	protected List<Material> materials;

	/**
	 * The service categories list. <br>
	 * La liste des catégories de service.
	 */
	protected List<ServiceCat> serviceCats;

	/**
	 * The services list. <br>
	 * La liste des services.
	 */
	protected List<Service> services;

	/**
	 * The incidents list. <br>
	 * La liste des incidents.
	 */
	protected List<Incident> incidents;

	/**
	 * The details list. <br>
	 * La liste des détails.
	 */
	protected List<Detail> details;

	/**
	 * The Map of messages used in the app by title. <br>
	 * La Map des messages utilisés dans l'application par titre.
	 */
	protected Map<String, String> messages;

	/**
	 * The Map of labels exported from Osgrim by title. <br>
	 * La Map des libellés exportés depuis Osgrim par titre.
	 */
	protected Map<String, String> labels;

	/**
	 * The saved reports list. <br>
	 * La liste des rapports enregistrés.
	 */
	protected List<Report> reports;

	/**
	 * The reportAdapter that allows to display the list of reports in the reports list tab. <br>
	 * L'adaptateur de rapport qui permet d'afficher la liste des rapports dans l'onglet liste
	 * des rapports.
	 */
	protected ReportAdapter reportAdapter;

	/**
	 * The tabLayout which contains the three tabs of the application. <br>
	 * Le tabLayout qui contient les trois onglets de l'application.
	 */
	protected TabLayout tabLayout;

	/**
	 * The RecyclerView that displays the list of reports in the first tab of the application. <br>
	 * La RecyclerView qui affiche la liste des rapports dans le premier onglet de l'application.
	 */
	protected RecyclerView listReports;

	/**
	 * A BooleanVariable that has a handler when it changes of value. <br>
	 * Une BooleanVariable qui est associée à un évènement quand il change de valeur.
	 */
	protected BooleanVariable needClear;

	/**
	 * Object that allows us to save data when the user quit or leave the app. <br>
	 * Objet qui nous permet d'enregistrer des données quand l'utilisateur quitte ou part de
	 * l'application.
	 */
	protected SharedPreferences settings;

	/**
	 * The inputFragment is the class that contains the report input. It's needed to use its
	 * method "clear" that erases all the reports input field. <br>
	 * inputFragment est la classe qui contient la saisie de rapport. C'est nécessaire pour
	 * utiliser sa méthode "clear" qui efface tous les champs de saisie de rapport.
	 */
	protected InputFragment inputFragment;

	/**
	 * False if no data was imported. Data needs to be imported in order to access the first and
	 * second tab. <br>
	 * Faux si il n'y a pas eu de données importées. Les données doivent être importées afin
	 * d'accéder au premier et second onglets.
	 */
	protected boolean isDataImported = false;

	/**
	 * True if it's the first time the data was imported. The other times, the application will
	 * ask for confirmation before erase and import new data. <br>
	 * Vrai si c'est la première fois que les données sont importées. Les autres fois,
	 * l'application demandera une confirmation avant d'effacer et d'importer de nouvelles données.
	 */
	protected boolean isFirstTime = true;

	/**
	 * newImportData is true if an importation has just happened. <br>
	 * newImportData est vrai quand une importation vient juste de se passer;
	 */
	protected boolean newImportData = false;

	/**
	 * True if we have to display the space category and the space. <br>
	 * Vrai si on doit afficher la catégorie de local et le local.
	 */
	protected boolean isLocalDisplay;

	/**
	 * Method executed at the launching of the application. Declares the different tabs,
	 * initialize the lists and handle the saved data. <br>
	 * Méthode exécutée au lancement de l'application. Déclare les différents onglets, initialise
	 * les listes et gère les données enregistrées.
	 * @param savedInstanceState The instance of the saved state. <br> L'instance de l'état sauvegardé.
	 */
	@Override
	@SuppressWarnings("ConstantConditions")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the application messages (the ones that aren't imported from osgrim)
		// Récupère les messages de l'application (ceux qui ne sont pas importés depuis osgrim)
		getMessages();
		needClear = new BooleanVariable();

		// Set the tab layout
		tabLayout = findViewById(R.id.tablayout);
		ViewPager viewPager = findViewById(R.id.viewPager);

		PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
		viewPager.setAdapter(pageAdapter);

		viewPager.setOffscreenPageLimit(3);
		tabLayout.setupWithViewPager(viewPager);

		String[] titleTabs = { messages.get("reportList"), messages.get("inputReport"), messages.get("dataManagement") };

		// There is a warning but the NullPointerException is never raised.
		// Il y a un warning mais NullPointerException n'est jamais levée.
		for (int i = 0; i < tabLayout.getTabCount(); i++)
			tabLayout.getTabAt(i).setText(titleTabs[i]);

		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setLogo(R.drawable.logo_new);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setTitle("sgrim");

		// Initialize the lists and get saved data if there is any.
		// Initialise les listes et récupère les données sauvegardées s'il y en a.
		initializeLists();
		getStoredData();

		// Declares the reportAdapter for the reports list and displaying in the list tab.
		// Déclare l'adaptateur de rapport pour la liste des rapports
		this.reportAdapter = new ReportAdapter(this, this.reports, this.inputFragment);

		// If there is no data imported the user is stuck on the last tab until he imports the
		// data.
		// S'il n'y a pas de données importées l'utilisateur est bloqué au dernier onglet jusqu'à
		// ce qu'il importe les données.
		if (!isDataImported)
			tabLayout.getTabAt(2).select();

		// If data was just imported a message is displaying to tell the user the data was
		// imported.
		// Si les données viennent juste d'être importées, un message s'affiche pour dire à
		// l'utilisateur que les données ont été importées.
		if (newImportData)
			this.makeAlertInfo(this.messages.get("validImport"));

		/*
		Class used when the user changes of tab.
		Classe utilisée quand l'utilisateur change d'onglet.
		 */
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			/**
			 * When the data isn't imported, the selected tab stays the third tab. Else, the
			 * input tab is cleared. <br>
			 * Quand les données ne sont pas importées, l'onglet sélectionné reste le troisième
			 * onglet. Sinon, l'onglet de saisie est effacé.
			 * @param position The tab index: 0, 1 or 2. <br> L'index de l'onglet: 0, 1 ou 2.
			 */
			@Override
			public void onPageSelected(int position) {
				if (!isDataImported && position != 2)
					tabLayout.getTabAt(2).select();
				if (isFirstTime && position == 1 && isDataImported) {
					inputFragment.clear();
					isFirstTime = false;
					newImportData = false;
				} else if (newImportData && position == 1) {
					inputFragment.clear();
					newImportData = false;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	/**
	 * onPause is executed when the user leaves or kill the application. <br>
	 * onPause est exécuté quand l'utilisateur quitte ou part de l'application.
	 */
	@Override
	public void onPause() {
		super.onPause();
		setStoredData();
	}

	/**
	 * Saves the application data in the settings variable. <br>
	 * Enregistre les données de l'application dans la variable settings.
	 */
	public void setStoredData() {
		Context context = getApplicationContext();
		settings = context.getSharedPreferences("osgrim", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		/*
		All the lists are saved in json format.
		Toutes les listes sont enregistrées en format json.
		 */
		Gson gson = new Gson();
		String reportsJson = gson.toJson(reports);
		editor.putString("reports", reportsJson);

		String lblJson = gson.toJson(labels);
		editor.putString("labels", lblJson);

		String teamsJson = gson.toJson(teams);
		editor.putString("teams", teamsJson);

		String usersJson = gson.toJson(users);
		editor.putString("users", usersJson);

		String zonesJson = gson.toJson(zones);
		editor.putString("zones", zonesJson);

		String buildJson = gson.toJson(buildings);
		editor.putString("buildings", buildJson);

		String lvlJson = gson.toJson(levels);
		editor.putString("levels", lvlJson);

		String cantonsJson = gson.toJson(cantons);
		editor.putString("cantons", cantonsJson);

		String spaceCatJson = gson.toJson(spaceCats);
		editor.putString("spaceCats", spaceCatJson);

		String spaceJson = gson.toJson(spaces);
		editor.putString("spaces", spaceJson);

		String natureJson = gson.toJson(natures);
		editor.putString("natures", natureJson);

		String freqJson = gson.toJson(frequencies);
		editor.putString("frequencies", freqJson);

		String materialJson = gson.toJson(materials);
		editor.putString("materials", materialJson);

		String serviceCatJson = gson.toJson(serviceCats);
		editor.putString("serviceCats", serviceCatJson);

		String serviceJson = gson.toJson(services);
		editor.putString("services", serviceJson);

		String incidentsJson = gson.toJson(incidents);
		editor.putString("incidents", incidentsJson);

		String detailsJson = gson.toJson(details);
		editor.putString("details", detailsJson);

		editor.putBoolean("isDataImported", isDataImported);
		editor.putBoolean("isFirstTime", isFirstTime);
		editor.putBoolean("newImportData", newImportData);
		editor.putBoolean("isLocalDisplay", isLocalDisplay);

		editor.apply();
	}

	/**
	 * Get the saved data of the application. <br>
	 * Récupère les données enregistrées de l'application.
	 */
	public void getStoredData() {
		Context context = getApplicationContext();
		settings = context.getSharedPreferences("osgrim", Context.MODE_PRIVATE);
		Gson gson = new Gson();
		Type type;

		/*
		All the lists are filled with the previous values.
		Toutes les listes sont remplies avec les valeurs précédentes.
		 */
		String reportsJson = settings.getString("reports", "");
		if (reportsJson.length() > 0) {
			type = new TypeToken<List<Report>>() {}.getType();
			reports = gson.fromJson(reportsJson, type);
		}

		String lblJson = settings.getString("labels", "");
		if (lblJson.length() > 0) {
			type = new TypeToken<Map<String, String>>() {}.getType();
			labels = gson.fromJson(lblJson, type);
		}

		String teamsJson = settings.getString("teams", "");
		if (teamsJson.length() > 0) {
			type = new TypeToken<List<Team>>() {}.getType();
			teams = gson.fromJson(teamsJson, type);
		}

		String usersJson = settings.getString("users", "");
		if (usersJson.length() > 0) {
			type = new TypeToken<List<User>>() {}.getType();
			users = gson.fromJson(usersJson, type);
		}

		String zonesJson = settings.getString("zones", "");
		if (zonesJson.length() > 0) {
			type = new TypeToken<List<Zone>>() {}.getType();
			zones = gson.fromJson(zonesJson, type);
		}

		String buildJson = settings.getString("buildings", "");
		if (buildJson.length() > 0) {
			type = new TypeToken<List<Building>>() {}.getType();
			buildings = gson.fromJson(buildJson, type);
		}

		String lvlJson = settings.getString("levels", "");
		if (lvlJson.length() > 0) {
			type = new TypeToken<List<Level>>() {}.getType();
			levels = gson.fromJson(lvlJson, type);
		}

		String cantonsJson = settings.getString("cantons", "");
		if (cantonsJson.length() > 0) {
			type = new TypeToken<List<Canton>>() {}.getType();
			cantons = gson.fromJson(cantonsJson, type);
		}

		String spaceCatJson = settings.getString("spaceCats", "");
		if (spaceCatJson.length() > 0) {
			type = new TypeToken<List<SpaceCat>>() {}.getType();
			spaceCats = gson.fromJson(spaceCatJson, type);
		}

		String spacesJson = settings.getString("spaces", "");
		if (spacesJson.length() > 0) {
			type = new TypeToken<List<Space>>() {}.getType();
			spaces = gson.fromJson(spacesJson, type);
		}

		String natJson = settings.getString("natures", "");
		if (natJson.length() > 0) {
			type = new TypeToken<List<Nature>>() {}.getType();
			natures = gson.fromJson(natJson, type);
		}

		String freqJson = settings.getString("frequencies", "");
		if (freqJson.length() > 0) {
			type = new TypeToken<List<Frequency>>() {}.getType();
			frequencies = gson.fromJson(freqJson, type);
		}

		String materialJson = settings.getString("materials", "");
		if (materialJson.length() > 0) {
			type = new TypeToken<List<Material>>() {}.getType();
			materials = gson.fromJson(materialJson, type);
		}

		String serviceCatJson = settings.getString("serviceCats", "");
		if (serviceCatJson.length() > 0) {
			type = new TypeToken<List<ServiceCat>>() {}.getType();
			serviceCats = gson.fromJson(serviceCatJson, type);
		}

		String serviceJson = settings.getString("services", "");
		if (serviceJson.length() > 0) {
			type = new TypeToken<List<Service>>() {}.getType();
			services = gson.fromJson(serviceJson, type);
		}

		String incidentJson = settings.getString("incidents", "");
		if (incidentJson.length() > 0) {
			type = new TypeToken<List<Incident>>() {}.getType();
			incidents = gson.fromJson(incidentJson, type);
		}

		String detailsJson = settings.getString("details", "");
		if (detailsJson.length() > 0) {
			type = new TypeToken<List<Detail>>() {}.getType();
			details = gson.fromJson(detailsJson, type);
		}

		isDataImported = settings.getBoolean("isDataImported", false);
		isFirstTime = settings.getBoolean("isFirstTime", true);
		newImportData = settings.getBoolean("newImportData", false);
		isLocalDisplay = settings.getBoolean("isLocalDisplay", false);
	}

	/**
	 * Change the selected tab to the one at the position numberTab. <br>
	 * Change l'onglet sélectionné à celui positionné numberTab.
	 * @param numberTab The index of the tab (0,1,2). <br> L'index de l'onglet (0,1,2).
	 */
	@SuppressWarnings("ConstantConditions")
	protected void changeTab(int numberTab) {
		tabLayout.getTabAt(numberTab).select();
	}

	/**
	 * All the lists are initialized in order to be not null and ready to be filled. <br>
	 * Toutes les listes sont initialisées afin qu'elles ne soient pas null et prêtes à être
	 * remplies.
	 */
	protected void initializeLists() {
		this.users = new ArrayList<>();
		this.teams = new ArrayList<>();
		this.levels = new ArrayList<>();
		this.buildings = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.cantons = new ArrayList<>();
		this.spaceCats = new ArrayList<>();
		this.spaces = new ArrayList<>();
		this.natures = new ArrayList<>();
		this.frequencies = new ArrayList<>();
		this.materials = new ArrayList<>();
		this.serviceCats = new ArrayList<>();
		this.services = new ArrayList<>();
		this.incidents = new ArrayList<>();
		this.details = new ArrayList<>();

		this.reports = new ArrayList<>();
	}

	/**
	 * Get the data of the file written by the fat client osgrim software and fill the lists with
	 * this data. <br>
	 * Récupère les données du fichier écrit par le logiciel osgrim client lourd et remplie les
	 * listes avec ces données.
	 * @return True if no error occured. <br> Vrai s'il n'y a pas eu d'erreur.
	 */
	protected boolean getData() {
		initializeLists();

		try {
			// Get the principal JSON object
			JSONObject jObj = new JSONObject(readJSON("data_transfer.json", false));

			// Check if we have to display the space category and space spinners
			// Check si on doit afficher la liste déroulante de catégorie de local et de local
			isLocalDisplay = !(jObj.getInt("local") == 0);

			// Get the users and fill the users list
			JSONArray users = jObj.getJSONArray("users");
			for (int i = 0; i < users.length(); i++) {
				JSONObject user = users.getJSONObject(i);
				this.users.add(new User(user.getInt("id"), user.getString("lastname"), user.getString("firstname")));
			}

			// Get the teams and fill the teams list
			JSONArray teams = jObj.getJSONArray("teams");
			for (int i = 0; i < teams.length(); i++) {
				JSONObject team = teams.getJSONObject(i);
				Team newTeam = new Team(team.getInt("id"), team.getString("name"));
				JSONArray usersId = team.getJSONArray("members");

				for (int j = 0; j < usersId.length(); j++) {
					for (User usr : this.users) {
						if (usr.getId() == usersId.getInt(j)) {
							newTeam.addMember(usr);
							break;
						}
					}
				}

				this.teams.add(newTeam);
			}
			this.teams.add(new Team(-1, ""));

			// Get the address
			// Get the levels
			JSONObject address = jObj.getJSONObject("address");
			JSONArray levels = address.getJSONArray("levels");

			for (int i = 0; i < levels.length(); i++) {
				JSONObject level = levels.getJSONObject(i);
				this.levels.add(new Level(level.getInt("id"), level.getString("name")));
			}

			// Get the buildings
			JSONArray buildings = address.getJSONArray("buildings");
			for (int i = 0; i < buildings.length(); i++) {
				JSONObject building = buildings.getJSONObject(i);
				Building newBuilding = new Building(building.getInt("id"), building.getString("name"));
				JSONArray levelsId = building.getJSONArray("levels");

				for (int j = 0; j < levelsId.length(); j++) {
					for (Level lvl : this.levels) {
						if (lvl.getId() == levelsId.getInt(j)) {
							newBuilding.addLevel(lvl);
							break;
						}
					}
				}

				this.buildings.add(newBuilding);
			}

			// Get the zones
			JSONArray zones = address.getJSONArray("zones");
			for (int i = 0; i < zones.length(); i++) {
				JSONObject zone = zones.getJSONObject(i);
				Zone newZone = new Zone(zone.getInt("id"), zone.getString("name"));
				JSONArray buildingsId = zone.getJSONArray("buildings");

				for (int j = 0; j < buildingsId.length(); j++) {
					for (Building bld : this.buildings) {
						if (bld.getId() == buildingsId.getInt(j)) {
							newZone.addBuilding(bld);
							break;
						}
					}
				}

				this.zones.add(newZone);
			}
			this.zones.add(new Zone(-1, ""));

			// Get the cantons
			JSONArray cantons = address.getJSONArray("cantons");
			for (int i = 0; i < cantons.length(); i++) {
				JSONObject canton = cantons.getJSONObject(i);
				this.cantons.add(new Canton(canton.getInt("id"), canton.getString("name")));
			}
			this.cantons.add(new Canton(-1, ""));

			// Get the spaces
			JSONArray spaces = address.getJSONArray("spaces");
			for (int i = 0; i < spaces.length(); i++) {
				JSONObject space = spaces.getJSONObject(i);
				this.spaces.add(new Space(space.getInt("id"), space.getString("name")));
			}

			// Get the spaceCats
			JSONArray spaceCats = address.getJSONArray("spaceCats");
			for (int i = 0; i < spaceCats.length(); i++) {
				JSONObject spaceCat = spaceCats.getJSONObject(i);
				SpaceCat newSpaceCat = new SpaceCat(spaceCat.getInt("id"), spaceCat.getString("name"));
				JSONArray spacesId = spaceCat.getJSONArray("spaces");

				for (int j = 0; j < spacesId.length(); j++) {
					for (Space spc : this.spaces) {
						if (spc.getId() == spacesId.getInt(j)) {
							newSpaceCat.addSpace(spc);
							break;
						}
					}
				}

				this.spaceCats.add(newSpaceCat);
			}
			this.spaceCats.add(new SpaceCat(-1, ""));

			// Get the maintenance
			JSONObject maintenance = jObj.getJSONObject("maintenance");

			// Get the nature operation
			JSONArray natures = maintenance.getJSONArray("natures");
			for (int i = 0; i < natures.length(); i++) {
				JSONObject nature = natures.getJSONObject(i);
				this.natures.add(new Nature(nature.getInt("id"), nature.getString("name")));
			}
			this.natures.add(new Nature(-1, ""));

			// Get the frequency
			JSONArray frequencies = maintenance.getJSONArray("frequencies");
			for (int i = 0; i < frequencies.length(); i++) {
				JSONObject frequency = frequencies.getJSONObject(i);
				this.frequencies.add(new Frequency(frequency.getInt("id"), frequency.getString("name")));
			}
			this.frequencies.add(new Frequency(-1, ""));

			// Get the incidents
			JSONArray incidents = jObj.getJSONArray("incidents");
			for (int i = 0; i < incidents.length(); i++) {
				JSONObject incident = incidents.getJSONObject(i);
				this.incidents.add(new Incident(incident.getInt("id"), incident.getString("name")));
			}

			// Get the material
			JSONArray materials = maintenance.getJSONArray("materials");
			for (int i = 0; i < materials.length(); i++) {
				JSONObject material = materials.getJSONObject(i);
				Material newMaterial = new Material(material.getInt("id"), material.getString("name"));
				JSONArray incidentsId = material.getJSONArray("incidents");

				for (int j = 0; j < incidentsId.length(); j++) {
					for (Incident icd : this.incidents) {
						if (icd.getId() == incidentsId.getInt(j)) {
							newMaterial.addIncident(icd);
							break;
						}
					}
				}

				this.materials.add(newMaterial);
			}
			this.materials.add(new Material(-1, ""));

			// Get the categories of service
			JSONArray serviceCats = jObj.getJSONArray("serviceCats");
			for (int i = 0; i < serviceCats.length(); i++) {
				JSONObject serviceCat = serviceCats.getJSONObject(i);
				ServiceCat newServiceCat = new ServiceCat(serviceCat.getInt("id"), serviceCat.getString("name"));
				JSONArray services = serviceCat.getJSONArray("services");

				for (int j = 0; j < services.length(); j++) {
					JSONObject jsonService = services.getJSONObject(j);
					Service service = new Service(jsonService.getInt("id"), jsonService.getString("name"));
					this.services.add(service);
					newServiceCat.addService(service);
				}

				this.serviceCats.add(newServiceCat);
			}

			// Get the details
			JSONArray details = jObj.getJSONArray("details");
			for (int i = 0; i < details.length(); i++) {
				JSONObject detail = details.getJSONObject(i);
				Detail newDetail = new Detail(detail.getString("title"));
				JSONArray answers = detail.getJSONArray("answers");

				for (int j = 0; j < answers.length(); j++)
					newDetail.addAnswer(answers.getString(j));

				newDetail.addAnswer("");

				this.details.add(newDetail);
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Get all the messages used in the application from the json file in the assets and put them
	 * in the messages map. <br>
	 * Récupère tous les messages utilisés dans l'application depuis le fichier json dans les
	 * assets et les insère dans la map messages.
	 */
	private void getMessages() {
		this.messages = new HashMap<>();
		try {
			JSONObject jObj = new JSONObject(readJSON("messages.json", true));

			this.messages.put("error", jObj.getString("error"));
			this.messages.put("natureMissing", jObj.getString("natureMissing"));
			this.messages.put("dateMissing", jObj.getString("dateMissing"));
			this.messages.put("timeMissing", jObj.getString("timeMissing"));
			this.messages.put("incoherent", jObj.getString("incoherent"));
			this.messages.put("cancel", jObj.getString("cancel"));
			this.messages.put("ok", jObj.getString("ok"));
			this.messages.put("nbIncident", jObj.getString("nbIncident"));
			this.messages.put("titleNbIncident", jObj.getString("titleNbIncident"));
			this.messages.put("clear", jObj.getString("clear"));
			this.messages.put("confirmErase", jObj.getString("confirmErase"));
			this.messages.put("clearComplete", jObj.getString("clearComplete"));
			this.messages.put("saveComplete", jObj.getString("saveComplete"));
			this.messages.put("reportList", jObj.getString("reportList"));
			this.messages.put("inputReport", jObj.getString("inputReport"));
			this.messages.put("dataManagement", jObj.getString("dataManagement"));
			this.messages.put("enterStartDate", jObj.getString("enterStartDate"));
			this.messages.put("enterStartTime", jObj.getString("enterStartTime"));
			this.messages.put("yes", jObj.getString("yes"));
			this.messages.put("no", jObj.getString("no"));
			this.messages.put("info", jObj.getString("info"));
			this.messages.put("editReport", jObj.getString("editReport"));
			this.messages.put("confirmDelete", jObj.getString("confirmDelete"));
			this.messages.put("deleted", jObj.getString("deleted"));
			this.messages.put("instructions", jObj.getString("instructions"));
			this.messages.put("importData", jObj.getString("importData"));
			this.messages.put("exportData", jObj.getString("exportData"));
			this.messages.put("validImport", jObj.getString("validImport"));
			this.messages.put("validExport", jObj.getString("validExport"));
			this.messages.put("warningImport", jObj.getString("warningImport"));
			this.messages.put("errorImport", jObj.getString("errorImport"));
			this.messages.put("noData", jObj.getString("noData"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the labels indicated in the file written by the fat client osgrim software and fill the
	 * labels list with this data. <br>
	 * Récupère les libellés du fichier écrit par le logiciel osgrim client lourd et remplie la
	 * liste labels avec ces données.
	 */
	protected void getLabels() {
		this.labels = new HashMap<>();
		try {
			JSONObject jObj = new JSONObject(readJSON("label.json", false));

			this.labels.put("team", jObj.getString("team"));
			this.labels.put("user", jObj.getString("user"));
			this.labels.put("address", jObj.getString("address"));
			this.labels.put("zone", jObj.getString("zone"));
			this.labels.put("building", jObj.getString("building"));
			this.labels.put("level", jObj.getString("level"));
			this.labels.put("canton", jObj.getString("canton"));
			this.labels.put("pillar", jObj.getString("pillar"));
			this.labels.put("spaceCat", jObj.getString("spaceCat"));
			this.labels.put("space", jObj.getString("space"));
			this.labels.put("maintenance", jObj.getString("maintenance"));
			this.labels.put("nature", jObj.getString("nature"));
			this.labels.put("frequency", jObj.getString("frequency"));
			this.labels.put("material", jObj.getString("material"));
			this.labels.put("code", jObj.getString("code"));
			this.labels.put("startDate", jObj.getString("startDate"));
			this.labels.put("endDate", jObj.getString("endDate"));
			this.labels.put("startDate2", jObj.getString("startDate2"));
			this.labels.put("endDate2", jObj.getString("endDate2"));
			this.labels.put("startTime", jObj.getString("startTime"));
			this.labels.put("endTime", jObj.getString("endTime"));
			this.labels.put("services", jObj.getString("services"));
			this.labels.put("teams", jObj.getString("teams"));
			this.labels.put("participant", jObj.getString("participant"));
			this.labels.put("participants", jObj.getString("participants"));
			this.labels.put("incident", jObj.getString("incident"));
			this.labels.put("comment", jObj.getString("comment"));
			this.labels.put("details", jObj.getString("details"));
			this.labels.put("save", jObj.getString("save"));
			this.labels.put("draft", jObj.getString("draft"));
			this.labels.put("cancel", jObj.getString("cancel"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read a json file named name.json in the download folder of the device and return the
	 * string content of the file. <br>
	 * Lit un fichier json appelé name.json dans le dossier download de l'appareil et retourne le
	 * contenu du fichier en chaîne de caractère.
	 * @param name The file name. <br> Le nom du fichier.
	 * @param isAsset True if the file is located in the assets, false if it's in the download
	 *                   folder. <br> Vrai si le fichier est localisé dans les assets, faux si il est
	 *                   dans le dossier download.
	 * @return The string content of the file. <br> Le contenu en string du fichier.
	 */
	@SuppressWarnings("unused")
	protected String readJSON(String name, boolean isAsset) {
		String json;
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + name);

		try {
			// Read from asset or download
			InputStream is = isAsset ? getAssets().open(name) : new FileInputStream(file);
			int size = is.available();
			byte[] buffer = new byte[size];
			int res = is.read(buffer);
			is.close();
			json = new String(buffer, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}

	/**
	 * Set the inputFragment so that the MainActivity has a reference to the class. Method called
	 * from InputFragment. <br>
	 * Fixe l'inputFragment pour que MainActivity ait une référence vers la classe. Méthode
	 * appelée depuis InputFragment.
	 * @param inputFragment The InputFragment class associated to MainActivity. <br> La classe
	 *                         InputFragment associée à MainActivity.
	 */
	protected void setInputFragment(InputFragment inputFragment) {
		this.inputFragment = inputFragment;
	}

	/**
	 * Makes a popup window with the message given in parameter. <br>
	 * Créé une fenêtre popup avec le message donné en paramètre.
	 * @param msg The message that will be displayed on the popup. <br> Le message qui va être affiché
	 *              sur la popup.
	 */
	protected void makeAlertInfo(String msg) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setMessage(msg);

		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, messages.get("info"),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.dismiss();
					}
				});

		alertDialog.show();

		TextView messageView = alertDialog.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);

		// Center the OK button
		// Centrer le bouton OK
		Button btnInfo = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
		LinearLayout parent = (LinearLayout) btnInfo.getParent();
		parent.setGravity(Gravity.CENTER_HORIZONTAL);
		View leftSpacer = parent.getChildAt(1);
		leftSpacer.setVisibility(View.GONE);
	}

	/**
	 * If the permissions to read the device files are given, the data stored in the download
	 * folder will be imported and the application will be ready to use. <br>
	 * Si les permissions pour lire les fichiers de l'appareil sont données, les données stockées
	 * dans le dossier download seront importées et l'application sera prête à l'usage.
	 */
	public void importData() {
		if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
		} else {
			try {
				getData();
				getLabels();

				this.reportAdapter = new ReportAdapter(this, this.reports, this.inputFragment);
				this.reportAdapter.setClickListener(this.inputFragment);
				this.listReports.setAdapter(this.reportAdapter);

				this.isDataImported = true;
				this.recreate();

				this.newImportData = true;
			} catch (Exception e) {
				makeAlertInfo(this.messages.get("errorImport"));
			}
		}
	}

	/**
	 * Executed when a permission is asked to the user. If the permission is granted, the data
	 * will be imported. <br>
	 * Exécuté quand une permission est demandée à l'utilisateur. Si la permission est accordée,
	 * les données seront importées.
	 * @param requestCode The requestCode. We don't use it. <br> Le code de requête. On ne s'en
	 *                       sert pas.
	 * @param permissions The array of asked permissions. <br> Le tableau de permissions demandées.
	 * @param grantResults The array of the responses where we can know if the permission is
	 *                        granted or not. <br> Le tableau de réponses où on peut savoir si la
	 *                        permission est accordée ou non.
	 */
	@Override
	public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,
										   int[] grantResults) {
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
				importData();
	}
}
