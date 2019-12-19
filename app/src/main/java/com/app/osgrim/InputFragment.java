package com.app.osgrim;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.osgrim.data.Building;
import com.app.osgrim.data.Canton;
import com.app.osgrim.data.Detail;
import com.app.osgrim.data.Frequency;
import com.app.osgrim.data.Incident;
import com.app.osgrim.data.Intervenant;
import com.app.osgrim.data.Level;
import com.app.osgrim.data.Material;
import com.app.osgrim.data.Nature;
import com.app.osgrim.data.Report;
import com.app.osgrim.data.Service;
import com.app.osgrim.data.Space;
import com.app.osgrim.data.SpaceCat;
import com.app.osgrim.data.Team;
import com.app.osgrim.data.User;
import com.app.osgrim.data.Zone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * The InputFragment class is the view of the report input.
 * We can see it by selecting the tab associated.
 * La classe InputFragment est la vue de saisie de rapport.
 * On la voit en sélectionnant l'onglet associé.
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment implements OnItemClickListener {
	// The view spinners
	// Les listes déroulantes de la vue
	// Some spinners are filled when an other is selected
	// Certaines listes sont remplies quand une autrea eu une valeur de sélectionnée
	private Spinner spinTeam, spinUser, spinZone, spinBuild, spinLvl, spinCanton, spinSpaceCat, spinSpace;
	private Spinner spinNature, spinFrequency, spinMaterial;
	private Spinner[] detailSpinners;
	private EditText etPillar, etCode, etDateStart, etTimeStart, etDateEnd, etTimeEnd, etComment;
	// The lists used to fill the spinners
	// Les listes utilisées pour remplir les listes déroulantes
	private List<User> users;
	private List<Building> buildings;
	private List<Level> levels;
	private List<Space> spaces;
	private MainActivity mainAct;
	private Calendar myCalendar;
	private RecyclerView listServOne, listServTwo, listInters, listIncident;
	private View inputView;
	private Button btnSave, btnClear, btnSaveEdit, btnCancelEdit;
	private ServiceAdapter servAdapterOne, servAdapterTwo;
	private IntervenantAdapter interAdapter;
	private IncidentAdapter incidentAdapter;
	private NestedScrollView scrollView;
	private LinearLayout layoutInput;
	private LinearLayout layoutEdit;
	private Report reportToEdit;

	public InputFragment() {
		// Required empty public constructor
	}

	/**
	 * onCreateView is called at the creation of the application.
	 * onCreateView est appelée à la création de l'application.
	 * @param inflater The inflater.
	 * @param container The container.
	 * @param savedInstanceState The saved state.
	 * @return The input view. La vue de saisie de rapport.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		// Récupération de la vue en cours
		inputView = inflater.inflate(R.layout.fragment_input, container, false);

		// Get all the element from the layout (fragment_input.xml)
		// Récupère les éléments du layout
		spinTeam = inputView.findViewById(R.id.spinTeam);
		spinUser = inputView.findViewById(R.id.spinUser);
		spinZone = inputView.findViewById(R.id.spinZone);
		spinBuild = inputView.findViewById(R.id.spinBuild);
		spinLvl = inputView.findViewById(R.id.spinLvl);
		spinCanton = inputView.findViewById(R.id.spinCanton);
		spinSpaceCat = inputView.findViewById(R.id.spinSpaceCat);
		spinSpace = inputView.findViewById(R.id.spinSpace);

		spinNature = inputView.findViewById(R.id.spinNature);
		spinFrequency = inputView.findViewById(R.id.spinFrequency);
		spinMaterial = inputView.findViewById(R.id.spinMaterial);
		etDateStart = inputView.findViewById(R.id.etDateStart);
		etDateEnd = inputView.findViewById(R.id.etDateEnd);
		etTimeStart = inputView.findViewById(R.id.etTimeStart);
		etTimeEnd = inputView.findViewById(R.id.etTimeEnd);

		listServOne = inputView.findViewById(R.id.lvService1);
		listServTwo = inputView.findViewById(R.id.lvService2);
		listInters = inputView.findViewById(R.id.lvIntervenants);
		listIncident = inputView.findViewById(R.id.lvIncidents);

		etPillar = inputView.findViewById(R.id.etPillar);
		etCode = inputView.findViewById(R.id.etCode);
		etComment = inputView.findViewById(R.id.etComment);

		btnSave = inputView.findViewById(R.id.btnSave);
		btnClear = inputView.findViewById(R.id.btnClear);
		btnSaveEdit = inputView.findViewById(R.id.btnSaveEdit);
		btnCancelEdit = inputView.findViewById(R.id.btnCancelEdit);

		scrollView = inputView.findViewById(R.id.inputScrollView);

		// mainAct is the MainActivity, it's needed because this class is a Fragment
		mainAct = (MainActivity) getActivity();

		listServOne.setLayoutManager(new LinearLayoutManager(mainAct));
		listServTwo.setLayoutManager(new LinearLayoutManager(mainAct));
		listInters.setLayoutManager(new LinearLayoutManager(mainAct));
		listIncident.setLayoutManager(new LinearLayoutManager(mainAct));

		layoutInput = inputView.findViewById(R.id.layoutButtonsInput);
		layoutEdit = inputView.findViewById(R.id.layoutButtonsEdit);

		// setHasOptionsMenu has to be true to Override onCreateOptionsMenu()
		// setHasOptionsMenu(true);

		// The initialization of all the elements are dispatched in multiple methods
		if (mainAct.isDataImported) {
			initializeElements();
			initializeText();
			initializeAddress();
			initializeMaintenance();
			initializeService();
			initializeInters();
			initializeIncident(null);
			initializeDetails();

			mainAct.reportAdapter.setClickListener(this);
		}

		mainAct.setInputFragment(this);

		// The view
		return inputView;
	}


	private void initializeElements() {
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog alertDialog = new AlertDialog.Builder(mainAct).create();
				alertDialog.setMessage(mainAct.messages.get("confirmErase"));

				alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, mainAct.messages.get("yes"),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								clear();
								Toast.makeText(mainAct, mainAct.messages.get("clearComplete"), Toast.LENGTH_SHORT).show();
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
			}
		});

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (checkFields())
					save(null);
			}
		});

		btnSaveEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				save(reportToEdit);
				cancel();
			}
		});

		btnCancelEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cancel();
				mainAct.changeTab(0);
			}
		});

		mainAct.needClear.setListener(new BooleanVariable.ChangeListener() {
			@Override
			public void onChange() {
				clear();
			}
		});
	}

	/**
	 * Initialize the address "bloc". The spinners are all filled.
	 * Initialise le "bloc" adresse. Les listes déroulantes sont toutes remplies.
	 */
	private void initializeAddress() {
		// Add a new empty Team to have a blank spinner before the user selects anything
		// Ajoute une nouvelle équipe vide pour avoir un blanc dans la liste déroulante avant que l'utilisateur sélecte une option

		// The ArrayAdapter permits to fill the team spinner with the teams list
		// L'objet ArrayAdapter permet de remplir la liste déroulante des équipes avec la liste des équipes
		ArrayAdapter<Team> adapter = new ArrayAdapter<Team>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.teams) {
			@Override
			public int getCount() {
				// The total size of the spinner items is reduced by one because the empty team isn't an option
				// La taille totale des éléments de la liste déroulante est réduite de un car l'équipe vide n'est pas une option
				return super.getCount() - 1;
			}
		};
		spinTeam.setAdapter(adapter);

		// The empty team is selected to have a blank entry
		// L'équipe vide est sélectionnée pour avoir une valeur nulle au début
		spinTeam.setSelection(mainAct.teams.size() - 1);

		// The team spinner listener fills the user spinner according to the selected team
		// La liste déroulante des équipes remplie la liste déroulante des utilisateurs selon l'équipe sélectionnée
		SpinnerTeamListener spinnerTeamListener = new SpinnerTeamListener();
		spinTeam.setOnTouchListener(spinnerTeamListener);
		spinTeam.setOnItemSelectedListener(spinnerTeamListener);

		// Same for the zones, empty zone and adapter that fills the spinner with the zones list
		// Idem pour les zones, une zone vide et un adapteur qui remplie la liste déroulante avec la liste des zones
		ArrayAdapter<Zone> zoneAdapter = new ArrayAdapter<Zone>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.zones) {
			@Override
			public int getCount() {
				return mainAct.zones.size() - 1;
			}
		};
		spinZone.setAdapter(zoneAdapter);

		spinZone.setSelection(mainAct.zones.size() - 1);

		// Listeners that permits to fill the building spinner according to the selected zone
		// Listener (écouteur) qui permet de remplir la liste déroulante des bâtiments selon la zone sélectionnée
		SpinnerZoneListener spinnerZoneListener = new SpinnerZoneListener();
		spinZone.setOnTouchListener(spinnerZoneListener);
		spinZone.setOnItemSelectedListener(spinnerZoneListener);

		SpinnerBuildingListener spinnerBuildingListener = new SpinnerBuildingListener();
		spinBuild.setOnTouchListener(spinnerBuildingListener);
		spinBuild.setOnItemSelectedListener(spinnerBuildingListener);

		ArrayAdapter<Canton> cantonAdapter = new ArrayAdapter<Canton>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.cantons) {
			@Override
			public int getCount() {
				return mainAct.cantons.size() - 1;
			}
		};
		spinCanton.setAdapter(cantonAdapter);
		spinCanton.setSelection(mainAct.cantons.size() - 1);


		LinearLayout llSpaceCat = inputView.findViewById(R.id.llSpaceCat);
		LinearLayout llSpace = inputView.findViewById(R.id.llSpace);
		if (mainAct.isLocalDisplay) {
			llSpaceCat.setVisibility(View.VISIBLE);
			llSpace.setVisibility(View.VISIBLE);

			ArrayAdapter<SpaceCat> spaceCatAdapter = new ArrayAdapter<SpaceCat>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.spaceCats) {
				@Override
				public int getCount() {
					return mainAct.spaceCats.size() - 1;
				}
			};
			spinSpaceCat.setAdapter(spaceCatAdapter);
			spinSpaceCat.setSelection(mainAct.spaceCats.size() - 1);

			SpinnerSpaceCatListener spinnerSpaceCatListener = new SpinnerSpaceCatListener();
			spinSpaceCat.setOnTouchListener(spinnerSpaceCatListener);
			spinSpaceCat.setOnItemSelectedListener(spinnerSpaceCatListener);
		} else {
			llSpaceCat.setVisibility(View.GONE);
			llSpace.setVisibility(View.GONE);
		}
	}

	private void initializeMaintenance() {
		ArrayAdapter<Nature> natureAdapter = new ArrayAdapter<Nature>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.natures) {
			@Override
			public int getCount() {
				return mainAct.natures.size() - 1;
			}
		};

		spinNature.setAdapter(natureAdapter);
		spinNature.setSelection(mainAct.natures.size() - 1);

		spinNature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				if (spinNature.getSelectedView() != null)
					((TextView) spinNature.getSelectedView()).setError(null);
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {

			}
		});

		// mainAct.frequencies.add(new Frequency(-1, ""));
		ArrayAdapter<Frequency> freqAdapter = new ArrayAdapter<Frequency>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.frequencies) {
			@Override
			public int getCount() {
				return mainAct.frequencies.size() - 1;
			}
		};

		spinFrequency.setAdapter(freqAdapter);
		spinFrequency.setSelection(mainAct.frequencies.size() - 1);

		// mainAct.materials.add(new Material(-1, ""));
		ArrayAdapter<Material> materialAdapter = new ArrayAdapter<Material>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.materials) {
			@Override
			public int getCount() {
				return mainAct.materials.size() - 1;
			}
		};

		spinMaterial.setAdapter(materialAdapter);
		spinMaterial.setSelection(mainAct.materials.size() - 1);

		SpinnerMaterialListener spinnerMaterialListener = new SpinnerMaterialListener();
		spinMaterial.setOnTouchListener(spinnerMaterialListener);
		spinMaterial.setOnItemSelectedListener(spinnerMaterialListener);

		myCalendar = Calendar.getInstance();

		final DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, month);
				myCalendar.set(Calendar.DAY_OF_MONTH, day);
				updateLabel(etDateStart);
			}
		};

		etDateStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				etDateStart.setError(null);
				new DatePickerDialog(mainAct, dateStart, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int month, int day) {
				myCalendar.set(Calendar.YEAR, year);
				myCalendar.set(Calendar.MONTH, month);
				myCalendar.set(Calendar.DAY_OF_MONTH, day);
				updateLabel(etDateEnd);
			}
		};

		etDateEnd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				etDateEnd.setError(null);
				etTimeEnd.setError(null);
				new DatePickerDialog(mainAct, dateEnd, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		etTimeStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				etTimeStart.setError(null);
				TimePickerDialog timePickerDialog = new TimePickerDialog(mainAct, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
						etTimeStart.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
					}
				}, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
				timePickerDialog.show();
			}
		});

		etTimeEnd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				etTimeEnd.setError(null);
				TimePickerDialog timePickerDialog = new TimePickerDialog(mainAct, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
						etTimeEnd.setText(String.format(Locale.getDefault(), "%02d:%02d", hours, minutes));
					}
				}, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true);
				timePickerDialog.show();
			}
		});
	}

	private void updateLabel(EditText et) {
		String format = "dd/MM/yy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
		et.setText(sdf.format(myCalendar.getTime()));
	}

	private void initializeService() {
		List<Service> servicesOne, servicesTwo;
		servicesOne = mainAct.serviceCats.get(0).getServices();
		servicesTwo = mainAct.serviceCats.get(1).getServices();

		servAdapterOne = new ServiceAdapter(getContext(), servicesOne);
		servAdapterTwo = new ServiceAdapter(getContext(), servicesTwo);


		listServOne.setAdapter(servAdapterOne);
		listServTwo.setAdapter(servAdapterTwo);
	}

	private void initializeInters() {
		List<Intervenant> inters = new ArrayList<>();

		for (Team team : mainAct.teams)
			for (User user : team.getMembers())
				inters.add(new Intervenant(team, user));

		interAdapter = new IntervenantAdapter(getContext(), inters);
		listInters.setAdapter(interAdapter);
	}

	private void initializeIncident(Material material) {
		List<Incident> incidents = (material != null) ? material.getIncidents() : null;

		if (incidents != null) {
			fillIncident(incidents);
		}
	}

	private void fillIncident(List<Incident> incidents) {
		incidentAdapter = new IncidentAdapter(getContext(), incidents);
		listIncident.setAdapter(incidentAdapter);
	}

	private void initializeDetails() {
		List<Detail> details = mainAct.details;
		detailSpinners = new Spinner[details.size()];

		for (int i = 0; i < details.size(); i++) {
			int tvID = getResources().getIdentifier("txtDetail" + (i + 1), "id", mainAct.getPackageName());
			int spinID = getResources().getIdentifier("spinDetail" + (i + 1), "id", mainAct.getPackageName());

			TextView txtDetail = inputView.findViewById(tvID);
			Spinner spinDetail = inputView.findViewById(spinID);

			txtDetail.setText(details.get(i).getTitle());
			List<String> answers = details.get(i).getAnswers();

			// answers.add("");
			final int answersSize = answers.size() - 1;

			ArrayAdapter<String> detailAdapter = new ArrayAdapter<String>(mainAct, R.layout.support_simple_spinner_dropdown_item, answers) {
				@Override
				public int getCount() {
					return answersSize;
				}
			};

			spinDetail.setAdapter(detailAdapter);

			spinDetail.setSelection(answersSize);

			spinDetail.setVisibility(View.VISIBLE);
			detailSpinners[i] = spinDetail;
		}

		LinearLayout layoutDetail = inputView.findViewById(R.id.layoutDetail);
		layoutDetail.setVisibility(View.VISIBLE);
	}

	private boolean checkFields() {
		Nature nature = (Nature) spinNature.getSelectedItem();
		if (nature.getId() == -1) {
			makeAlert(mainAct.messages.get("natureMissing"));
			((TextView) spinNature.getSelectedView()).setError("");
			spinNature.setFocusable(true);
			spinNature.setFocusableInTouchMode(true);
			spinNature.requestFocus();
			return false;
		}

		String strStartDate = etDateStart.getText().toString();
		String strStartTime = etTimeStart.getText().toString();
		String strEndDate = etDateEnd.getText().toString();
		String strEndTime = etTimeEnd.getText().toString();

		if (strStartDate.length() == 0) {
			makeAlert(mainAct.messages.get("dateMissing"));
			focusEditText(etDateStart);
			etDateStart.setError("");
			return false;
		}

		if (strStartTime.length() == 0) {
			makeAlert(mainAct.messages.get("timeMissing"));
			focusEditText(etTimeStart);
			etTimeStart.setError("");
			return false;
		}

		if (strEndDate.length() != 0) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yy", Locale.getDefault());
				Date startDate = simpleDateFormat.parse(strStartDate);
				Date endDate = simpleDateFormat.parse(strEndDate);

				if (startDate != null && startDate.after(endDate)) {
					makeAlert(mainAct.messages.get("incoherent"));
					focusEditText(etDateStart);
					etDateEnd.setError("");
					return false;
				}

				if (startDate != null && startDate.equals(endDate) && strEndTime.length() > 0) {
					SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
					Date startTime = timeFormat.parse(strStartTime);
					Date endTime = timeFormat.parse(strEndTime);

					if (startTime != null && startTime.after(endTime)) {
						makeAlert(mainAct.messages.get("incoherent"));
						focusEditText(etTimeStart);
						etTimeEnd.setError("");
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	private void focusEditText(EditText et) {
		et.setFocusable(true);
		et.setFocusableInTouchMode(true);
		et.requestFocus();
		et.setFocusableInTouchMode(false);
		et.setFocusable(false);
	}

	private void makeAlert(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mainAct);
		builder.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						dialogInterface.cancel();
					}
				});
		AlertDialog alert = builder.create();
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.weight = Float.parseFloat("1");
		layoutParams.gravity = Gravity.CENTER;
		alert.show();
		TextView messageView = alert.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
		alert.getButton(AlertDialog.BUTTON_POSITIVE).setLayoutParams(layoutParams);
	}

	private void save(Report repToSave) {
		Team team = (Team) spinTeam.getSelectedItem();
		User user = (User) spinUser.getSelectedItem();
		Zone zone = (Zone) spinZone.getSelectedItem();
		Building build = (Building) spinBuild.getSelectedItem();
		Level level = (Level) spinLvl.getSelectedItem();
		Canton canton = (Canton) spinCanton.getSelectedItem();
		SpaceCat spaceCat = (SpaceCat) spinSpaceCat.getSelectedItem();
		Space space = (Space) spinSpace.getSelectedItem();
		Nature nature = (Nature) spinNature.getSelectedItem();
		Frequency frequency = (Frequency) spinFrequency.getSelectedItem();
		Material material = (Material) spinMaterial.getSelectedItem();

		String pillar = etPillar.getText().toString();
		String code = etCode.getText().toString();
		String dateStart = etDateStart.getText().toString();
		String timeStart = etTimeStart.getText().toString();
		String dateEnd = etDateEnd.getText().toString();
		String timeEnd = etTimeEnd.getText().toString();
		String comment = etComment.getText().toString();

		List<Service> servicesOne = servAdapterOne.getSelectedItems();
		List<Service> serviceTwo = servAdapterTwo.getSelectedItems();
		List<Intervenant> intervenants = interAdapter.getSelectedItems();
		List<Incident> incidents = (incidentAdapter != null) ? incidentAdapter.getSelectedItems() : new ArrayList<Incident>();

		Map<Integer, List<Service>> serviceCatList = new HashMap<>();

		serviceCatList.put(mainAct.serviceCats.get(0).getId(), servicesOne);
		serviceCatList.put(mainAct.serviceCats.get(1).getId(), serviceTwo);

		Map<String, String> details = new LinkedHashMap<>();
		for (int i = 0; i < detailSpinners.length; i++) {
			int tvID = getResources().getIdentifier("txtDetail" + (i + 1), "id", mainAct.getPackageName());
			TextView tvTitle = inputView.findViewById(tvID);
			details.put(tvTitle.getText().toString(), (String) detailSpinners[i].getSelectedItem());
		}

		DateFormat df = new SimpleDateFormat("dd/MM/yy - HH:mm", Locale.getDefault());
		String currentDate = df.format(Calendar.getInstance().getTime());

		if (repToSave == null) {
			repToSave = new Report(nature, dateStart, timeStart, currentDate);
			mainAct.reports.add(repToSave);
		} else {
			repToSave.setNature(nature);
			repToSave.setStartDate(dateStart);
			repToSave.setStartTime(timeStart);
		}

		if (team.getId() != -1)
			repToSave.setTeam(team);

		if (user != null && user.getId() != -1)
			repToSave.setUser(user);

		if (zone.getId() != -1)
			repToSave.setZone(zone);

		if (build != null && build.getId() != -1)
			repToSave.setBuilding(build);

		if (level != null && level.getId() != -1)
			repToSave.setLevel(level);

		if (canton.getId() != -1)
			repToSave.setCanton(canton);

		if (mainAct.isLocalDisplay) {
			if (spaceCat.getId() != -1)
				repToSave.setSpaceCat(spaceCat);

			if (space != null && space.getId() != -1)
				repToSave.setSpace(space);
		}

		if (frequency.getId() != -1)
			repToSave.setFrequency(frequency);

		if (material.getId() != -1)
			repToSave.setMaterial(material);

		repToSave.setPillar(pillar);
		repToSave.setCode(code);
		repToSave.setEndDate(dateEnd);
		repToSave.setEndTime(timeEnd);
		repToSave.setComment(comment);

		repToSave.setServices(serviceCatList);
		repToSave.setIntervenants(intervenants);
		repToSave.setIncidents(incidents);
		repToSave.setDetails(details);

		mainAct.reportAdapter.notifyDataSetChanged();

		Toast.makeText(mainAct, mainAct.messages.get("saveComplete"), Toast.LENGTH_LONG).show();
		clear();
	}

	@SuppressLint("ClickableViewAccessibility")
	protected void clear() {
		spinTeam.setSelection(mainAct.teams.size() - 1);
		if (users != null)
			spinUser.setAdapter(null);
		spinZone.setSelection(mainAct.zones.size() - 1);
		if (buildings != null)
			spinBuild.setAdapter(null);
		if (levels != null)
			spinLvl.setAdapter(null);
		spinCanton.setSelection(mainAct.cantons.size() - 1);
		etPillar.setText("");
		spinSpaceCat.setSelection(mainAct.spaceCats.size() - 1);
		if (spaces != null)
			spinSpace.setAdapter(null);
		spinNature.setSelection(mainAct.natures.size() - 1);
		spinFrequency.setSelection(mainAct.frequencies.size() - 1);
		spinMaterial.setSelection(mainAct.materials.size() - 1);
		etCode.setText("");
		etDateStart.setText("");
		etTimeStart.setText("");
		etDateEnd.setText("");
		etTimeEnd.setText("");

		for (int i = 0; i < servAdapterOne.getItemCount(); i++)
			servAdapterOne.setSelected(i, false);
		servAdapterOne.notifyDataSetChanged();

		for (int i = 0; i < servAdapterTwo.getItemCount(); i++)
			servAdapterTwo.setSelected(i, false);
		servAdapterTwo.notifyDataSetChanged();

		for (int i = 0; i < interAdapter.getItemCount(); i++)
			interAdapter.setSelected(i, false);
		interAdapter.notifyDataSetChanged();

		if (incidentAdapter != null) {
			for (int i = 0; i < incidentAdapter.getItemCount(); i++)
				incidentAdapter.setSelected(i, false, 0);
			incidentAdapter.notifyDataSetChanged();
		}

		etComment.setText("");
		for (Spinner spinner : detailSpinners) {
			int idBlank = spinner.getAdapter().getCount();
			spinner.setSelection(idBlank);
		}

		scrollView.fullScroll(ScrollView.FOCUS_UP);
		ConstraintLayout cl = inputView.findViewById(R.id.constraintLayout);
		cl.requestFocus();
	}

	private void displayReport(Report report) {
		reportToEdit = report;
		layoutInput.setVisibility(View.INVISIBLE);
		layoutEdit.setVisibility(View.VISIBLE);

		selectValue(spinTeam, report.getTeam());
		fillUserSpinner(report.getTeam());
		selectValue(spinUser, report.getUser());
		selectValue(spinZone, report.getZone());
		fillBuildingSpinner(report.getZone());
		selectValue(spinBuild, report.getBuilding());
		fillLevelSpinner(report.getBuilding());
		selectValue(spinLvl, report.getLevel());
		selectValue(spinCanton, report.getCanton());
		selectValue(spinSpaceCat, report.getSpaceCat());
		fillSpaceSpinner(report.getSpaceCat());
		selectValue(spinSpace, report.getSpace());
		selectValue(spinNature, report.getNature());
		selectValue(spinFrequency, report.getFrequency());
		selectValue(spinMaterial, report.getMaterial());

		etPillar.setText(report.getPillar());
		etCode.setText(report.getCode());
		etDateStart.setText(report.getStartDate());
		etTimeStart.setText(report.getStartTime());
		etDateEnd.setText(report.getEndDate());
		etTimeEnd.setText(report.getEndTime());
		etComment.setText(report.getComment());

		List<Service> servicesOneSelected = report.getServices().get(mainAct.serviceCats.get(0).getId());
		if (servicesOneSelected == null) servicesOneSelected = new ArrayList<>();
		for (int i = 0; i < servAdapterOne.getItemCount(); i++) {
			for (Service service : servicesOneSelected) {
				if (service.getId() == servAdapterOne.getItem(i).getId())
					servAdapterOne.setSelected(i, true);
			}
		}

		List<Service> servicesTwoSelected = report.getServices().get(mainAct.serviceCats.get(1).getId());
		if (servicesTwoSelected == null) servicesTwoSelected = new ArrayList<>();
		for (int i = 0; i < servAdapterTwo.getItemCount(); i++) {
			for (Service service : servicesTwoSelected) {
				if (service.getId() == servAdapterTwo.getItem(i).getId())
					servAdapterTwo.setSelected(i, true);
			}
		}

		List<Intervenant> interSelected = report.getIntervenants();
		for (int i = 0; i < interAdapter.getItemCount(); i++) {
			for (Intervenant inter : interSelected) {
				if (inter.getTeam().getId() == interAdapter.getItem(i).getTeam().getId() && inter.getUser().getId() == interAdapter.getItem(i).getUser().getId())
					interAdapter.setSelected(i, true);
			}
		}

		List<Incident> allIncidents = (report.getMaterial() != null) ? report.getMaterial().getIncidents() : new ArrayList<Incident>();
		List<Incident> incidentSelected = report.getIncidents();

		for (Incident incident : allIncidents) {
			for (Incident selectIncident : incidentSelected) {
				if (incident.getId() == selectIncident.getId()) {
					incident.setSelected(true);
					incident.setNbrIncident(selectIncident.getNbrIncident());
				}
			}
		}
		fillIncident(allIncidents);

		servAdapterOne.notifyDataSetChanged();
		servAdapterTwo.notifyDataSetChanged();
		interAdapter.notifyDataSetChanged();

		initializeDetails();
		List<String> details = new ArrayList<>(report.getDetails().values());
		for (int i = 0; i < detailSpinners.length; i++) {
			if (details.get(i).length() != 0)
				detailSpinners[i].setSelection(mainAct.details.get(i).getAnswers().indexOf(details.get(i)));
		}

		mainAct.changeTab(1);
	}

	private void selectValue(Spinner spinner, Object value) {
		if (value == null)
			return;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (value.toString().equals(spinner.getItemAtPosition(i).toString())) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	private void cancel() {
		layoutEdit.setVisibility(View.INVISIBLE);
		layoutInput.setVisibility(View.VISIBLE);
		clear();
	}

	private void initializeText() {
		TextView txtTeam, txtUser, txtAddress, txtZone, txtBuild, txtLvl, txtCanton, txtPillar, txtSpaceCat, txtSpace;
		TextView txtMaintenance, txtNature, txtFrequency, txtMaterial, txtCode, txtStartDate, txtEndDate;
		TextView txtServices, txtService1, txtService2;
		TextView txtIntervenants, txtTeams, txtInters;
		TextView txtIncidents, txtIncident, txtComment;
		TextView txtDetails;

		txtTeam = inputView.findViewById(R.id.txtTeam);
		txtUser = inputView.findViewById(R.id.txtUser);
		txtAddress = inputView.findViewById(R.id.txtAddress);
		txtZone = inputView.findViewById(R.id.txtZone);
		txtBuild = inputView.findViewById(R.id.txtBuild);
		txtLvl = inputView.findViewById(R.id.txtLvl);
		txtCanton = inputView.findViewById(R.id.txtCanton);
		txtPillar = inputView.findViewById(R.id.txtPillar);
		txtSpaceCat = inputView.findViewById(R.id.txtSpaceCat);
		txtSpace = inputView.findViewById(R.id.txtSpace);
		txtMaintenance = inputView.findViewById(R.id.txtMaintenance);
		txtNature = inputView.findViewById(R.id.txtNature);
		txtFrequency = inputView.findViewById(R.id.txtFrequency);
		txtMaterial = inputView.findViewById(R.id.txtMaterial);
		txtCode = inputView.findViewById(R.id.txtCode);
		txtStartDate = inputView.findViewById(R.id.txtStartDate);
		txtEndDate = inputView.findViewById(R.id.txtEndDate);
		txtServices = inputView.findViewById(R.id.txtServices);
		txtService1 = inputView.findViewById(R.id.txtService1);
		txtService2 = inputView.findViewById(R.id.txtService2);
		txtIntervenants = inputView.findViewById(R.id.txtIntervenants);
		txtTeams = inputView.findViewById(R.id.txtTeams);
		txtInters = inputView.findViewById(R.id.txtInters);
		txtIncidents = inputView.findViewById(R.id.txtIncidents);
		txtIncident = inputView.findViewById(R.id.txtIncident);
		txtComment = inputView.findViewById(R.id.txtComments);
		txtDetails = inputView.findViewById(R.id.txtDetails);

		txtTeam.setText(mainAct.labels.get("team"));
		txtUser.setText(mainAct.labels.get("user"));
		txtAddress.setText(mainAct.labels.get("address"));
		txtZone.setText(mainAct.labels.get("zone"));
		txtBuild.setText(mainAct.labels.get("building"));
		txtLvl.setText(mainAct.labels.get("level"));
		txtCanton.setText(mainAct.labels.get("canton"));
		txtPillar.setText(mainAct.labels.get("pillar"));
		txtSpaceCat.setText(mainAct.labels.get("spaceCat"));
		txtSpace.setText(mainAct.labels.get("space"));
		txtMaintenance.setText(mainAct.labels.get("maintenance"));
		txtNature.setText(mainAct.labels.get("nature"));
		txtFrequency.setText(mainAct.labels.get("frequency"));
		txtMaterial.setText(mainAct.labels.get("material"));
		txtCode.setText(mainAct.labels.get("code"));
		txtStartDate.setText(mainAct.labels.get("startDate"));
		txtEndDate.setText(mainAct.labels.get("endDate"));
		txtServices.setText(mainAct.labels.get("services"));
		txtIntervenants.setText(mainAct.labels.get("participant"));
		txtInters.setText(mainAct.labels.get("participant"));
		txtTeams.setText(mainAct.labels.get("teams"));
		txtIncident.setText(mainAct.labels.get("incident"));
		txtIncidents.setText(mainAct.labels.get("incident"));
		txtComment.setText(mainAct.labels.get("comment"));
		txtDetails.setText(mainAct.labels.get("details"));

		btnSave.setText(mainAct.labels.get("save"));
		btnClear.setText(mainAct.messages.get("clear"));

		btnSaveEdit.setText(mainAct.messages.get("editReport"));
		btnCancelEdit.setText(mainAct.messages.get("cancel"));

		txtService1.setText(mainAct.serviceCats.get(0).getName());
		txtService2.setText(mainAct.serviceCats.get(1).getName());
	}

	@Override
	public void onConfigurationChanged(@NonNull Configuration config) {
		super.onConfigurationChanged(config);
	}

	@Override
	public void onClick(View view, int position) {
		// The onClick implementation of the RecyclerView item click
		Report report = mainAct.reports.get(position);
		clear();
		displayReport(report);
	}

	private void fillUserSpinner(Team team) {
		if (team == null)
			return;

		if (users != null)
			users = new ArrayList<>(); // Initialization of the users list if it's null

		// Get the members of the selected team
		// Récupération des membres de l'équipe sélectionnée
		users = team.getMembers();

		// Add an empty user for the null selection
		// Ajout d'un utilisateur vide pour la sélection vide
		users.add(new User(-1, "", ""));

		// User adapter to fill the spinner with the users list
		// Adapteur d'utilisateur pour remplir la liste déroulante avec la liste des utilisateurs (membres de l'équipe)
		ArrayAdapter<User> usrAdapter = new ArrayAdapter<User>(mainAct, R.layout.support_simple_spinner_dropdown_item, users) {
			@Override
			public int getCount() {
				return users.size() - 1;
			}
		};
		spinUser.setAdapter(usrAdapter);
		spinUser.setSelection(users.size() - 1);
	}

	private void fillBuildingSpinner(Zone zone) {
		if (zone == null)
			return;

		if (buildings != null)
			buildings = new ArrayList<>();

		// Get the buildings of the selected zone
		// Récupération des bâtiments de la zone sélectionnée
		buildings = zone.getBuildings();
		buildings.add(new Building(-1, ""));

		// Creation of the building adapter with the list of buildings
		// Création de l'adapteur de bâtiment avec la liste des bâtiments
		ArrayAdapter<Building> bldAdapter = new ArrayAdapter<Building>(mainAct, R.layout.support_simple_spinner_dropdown_item, buildings) {
			@Override
			public int getCount() {
				return buildings.size() - 1;
			}
		};
		spinBuild.setAdapter(bldAdapter);
		spinBuild.setSelection(buildings.size() - 1);
		// If there was a selected level, there isn't one anymore
		// S'il y avait un niveau de sélectionné, il n'y en a plus
		if (levels != null) {
			spinLvl.setAdapter(null);
		}
	}

	private void fillLevelSpinner(Building building) {
		if (building == null)
			return;

		if (levels != null)
			levels = new ArrayList<>();

		levels = building.getLevels();
		levels.add(new Level(-1, ""));

		ArrayAdapter<Level> lvlAdapter = new ArrayAdapter<Level>(mainAct, R.layout.support_simple_spinner_dropdown_item, levels) {
			@Override
			public int getCount() {
				return levels.size() - 1;
			}
		};
		spinLvl.setAdapter(lvlAdapter);
		spinLvl.setSelection(levels.size() - 1);
	}

	private void fillSpaceSpinner(SpaceCat spaceCat) {
		if (spaceCat == null)
			return;

		if (spaces != null)
			spaces = new ArrayList<>();

		spaces = spaceCat.getSpaces();
		spaces.add(new Space(-1, ""));

		ArrayAdapter<Space> spaceAdapter = new ArrayAdapter<Space>(mainAct, R.layout.support_simple_spinner_dropdown_item, spaces) {
			@Override
			public int getCount() {
				return spaces.size() - 1;
			}
		};
		spinSpace.setAdapter(spaceAdapter);
		spinSpace.setSelection(spaces.size() - 1);
	}

	private class SpinnerTeamListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			if (touched) {
				// Get the selected team
				// Récupération de l'équipe sélectionnée
				Team selectedTeam = (Team) spinTeam.getSelectedItem();
				fillUserSpinner(selectedTeam);
				touched = false;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	}

	private class SpinnerZoneListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			if (touched) {
				// Get the selected zone
				// Récupération de la zone sélectionnée
				Zone selectedZone = (Zone) spinZone.getSelectedItem();
				fillBuildingSpinner(selectedZone);
				touched = false;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	}

	private class SpinnerBuildingListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			if (touched) {
				Building selectedBuild = (Building) spinBuild.getSelectedItem();
				fillLevelSpinner(selectedBuild);
				touched = false;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	}

	private class SpinnerSpaceCatListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			if (touched) {
				SpaceCat selectedSpaceCat = (SpaceCat) spinSpaceCat.getSelectedItem();
				fillSpaceSpinner(selectedSpaceCat);
				touched = false;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	}

	private class SpinnerMaterialListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
			if (touched) {
				Material material = (Material) spinMaterial.getSelectedItem();
				initializeIncident(material);
				spinMaterial.requestFocus();
				touched = false;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> adapterView) {

		}
	}
}
