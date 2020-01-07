package com.app.osgrim;

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

public class MainActivity extends AppCompatActivity {

	protected List<Team> teams;
	protected List<User> users;
	protected List<Zone> zones;
	protected List<Building> buildings;
	protected List<Level> levels;
	protected List<Canton> cantons;
	protected List<SpaceCat> spaceCats;
	protected List<Space> spaces;
	protected List<Nature> natures;
	protected List<Frequency> frequencies;
	protected List<Material> materials;
	protected List<ServiceCat> serviceCats;
	protected List<Service> services;
	protected List<Incident> incidents;
	protected List<Detail> details;
	protected Map<String, String> messages;
	protected Map<String, String> labels;
	protected List<Report> reports;
	protected ReportAdapter reportAdapter;
	protected TabLayout tabLayout;
	protected RecyclerView listReports;
	protected BooleanVariable needClear;
	protected SharedPreferences settings;
	protected InputFragment inputFragment;
	protected boolean isDataImported = false;
	protected boolean isListsImported = false;
	protected boolean isFirstTime = true;
	protected boolean newImportData = false;
	protected boolean isLocalDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getMessages();
		needClear = new BooleanVariable();

		tabLayout = findViewById(R.id.tablayout);
		ViewPager viewPager = findViewById(R.id.viewPager);

		PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
		viewPager.setAdapter(pageAdapter);

		viewPager.setOffscreenPageLimit(3);
		tabLayout.setupWithViewPager(viewPager);

		String[] titleTabs = { messages.get("reportList"), messages.get("inputReport"), messages.get("dataManagement") };

		for (int i = 0; i < tabLayout.getTabCount(); i++)
			tabLayout.getTabAt(i).setText(titleTabs[i]);


		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setLogo(R.drawable.logo_new);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setTitle("sgrim");

		initializeLists();
		getStoredData();

		this.reportAdapter = new ReportAdapter(this, this.reports, this.inputFragment);

		if (!isDataImported)
			tabLayout.getTabAt(2).select();

		if (newImportData)
			this.makeAlertInfo(this.messages.get("validImport"));

		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

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

	@Override
	public void onPause() {
		super.onPause();
		setStoredData();
	}

	public void setStoredData() {
		Context context = getApplicationContext();
		settings = context.getSharedPreferences("osgrim", context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

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

	public void getStoredData() {
		Context context = getApplicationContext();
		settings = context.getSharedPreferences("osgrim", context.MODE_PRIVATE);
		Gson gson = new Gson();
		Type type;

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

		// Indicate to other classes that the lists have been imported
		isListsImported = isDataImported;
	}

	protected void changeTab(int numberTab) {
		tabLayout.getTabAt(numberTab).select();
	}

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

	protected boolean getData() {
		initializeLists();

		try {
			// Get the principal JSON object
			JSONObject jObj = new JSONObject(readJSONFromAsset("data_transfer.json"));
			// JSONObject jObj = new JSONObject(readJSONFromDownload("data_transfer.json"));

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

	private void getMessages() {
		this.messages = new HashMap<>();
		try {
			JSONObject jObj = new JSONObject(readJSONFromAsset("messages.json"));
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
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	protected void getLabels() {
		this.labels = new HashMap<>();
		try {
			JSONObject jObj = new JSONObject(readJSONFromAsset("label.json"));
			// JSONObject jObj = new JSONObject(readJSONFromDownload("label.json"));

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

	protected String readJSONFromDownload(String name) {
		String json;
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + name);
		// File file = new File(Environment.getExternalStorageDirectory() + File.separator + "Osgrim" + File.separator + name);
		try {
			InputStream is = new FileInputStream(file);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}

	protected String readJSONFromAsset(String name) {
		String json;
		try {
			InputStream is = getAssets().open(name);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}

	protected void setInputFragment(InputFragment inputFragment) {
		this.inputFragment = inputFragment;
	}

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

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE))
				importData();
	}
}
