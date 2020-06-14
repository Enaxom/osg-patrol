package com.app.osgrim;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.app.osgrim.data.State;
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
 * We can see it by selecting the tab associated. <br>
 * La classe InputFragment est la vue de saisie de rapport.
 * On la voit en sélectionnant l'onglet associé.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class InputFragment extends Fragment implements OnItemClickListener {

	/**
	 * The spinners used in the report input. <br>
	 * Les spinners (listes déroulantes) utilisés dans la saisie de rapport.
	 */
	private Spinner spinTeam, spinUser, spinZone, spinBuild, spinLvl, spinCanton, spinSpaceCat, spinSpace;
	private Spinner spinNature, spinFrequency, spinMaterial;

	/**
	 * The spinners of the details bloc. There are from 1 to 6 potential spinners for the details. <br>
	 * Les spinners du bloc détails. Il y a de 1 à 6 spinners potentiels pour les détails.
	 */
	private Spinner[] detailSpinners;

	/**
	 * The edittexts used in the report input. <br>
	 * Les edittexts (zone de texte) utilisées dans la saisie de rapport.
	 */
	private EditText etPillar, etCode, etDateStart, etTimeStart, etDateEnd, etTimeEnd, etComment;

	/**
	 * The lists used to fill the spinners. <br>
	 * Les listes utilisées pour remplir les spinners.
	 */
	private List<User> users; // The users who are members of the selected team. Les utilisateurs
	// membres de l'équipe sélectionnée.
	private List<Building> buildings; // The buildings which are associated to the selected zone.
	// Les bâtiments qui sont associés à la zone sélectionnée.
	private List<Level> levels; // The levels associated to the selected building. Les niveaux
	// associés au bâtiment sélectionné.
	private List<Space> spaces; // The spaces associated to the selected space category. Les
	// locaux associés à la catégorie de local sélectionnée.

	/**
	 * The MainActivity class. Needed because there are all the lists of the different elements
	 * in it. <br>
	 * La classe MainActivity. Nécessaire car il y a toutes les listes des différents éléments
	 * dedans.
	 */
	protected MainActivity mainAct;

	/**
	 * Calendar used to configure the EditText objects that let the user pick a date or time. <br>
	 * Calendrier utilisé pour configurer les zones de textes qui permettent à l'utilisateur de
	 * choisir une date ou une heure.
	 */
	private Calendar myCalendar;

	/**
	 * RecyclerView is a compenent that displays an element list. Used for all the checkbox lists. <br>
	 * RecyclerView est un composant qui affiche une liste d'éléments. Utilisé pour toutes les
	 * listes de cases à cocher.
	 */
	private RecyclerView listServOne, listServTwo, listInters, listIncident;

	/**
	 * The class itself. Needed because MainActivity needs to access this class to clear the
	 * input elements when new data is imported. <br>
	 * La classe en elle-même. Nécessaire car MainActivity a besoin d'accéder à cette classe pour
	 * effacer le contenu des éléments de saisie quand de nouvelles données sont importées.
	 */
	private View inputView;

	/**
	 * The buttons that can be displayed in the view. btnSave & btnClear when it's a report
	 * creation. btnSaveEdit & btnCancelEdit when it's a report edition. <br>
	 * Les boutons qui peuvent être affichés dans la vue. btnSave & btnCLear quand c'est une
	 * création de rapport. btnSaveEdit & btnCancelEdit quand c'est une modification de rapport.
	 */
	private Button btnSave, btnClear, btnSaveEdit, btnCancelEdit;

	/**
	 * The service adapters used to display the two lists of services. <br>
	 * Les adaptateurs de services utilisés pour afficher les deux listes de services.
	 */
	private ServiceAdapter servAdapterOne, servAdapterTwo;

	/**
	 * The intervenant adapter used to display the intervenants list (contributors). <br>
	 * L'adaptateur d'intervenant utilisé pour afficher la liste des intervenants.
	 */
	private IntervenantAdapter interAdapter;

	/**
	 * The incident adapter used to display the incidents list. <br>
	 * L'adaptateur d'incident utilisé pour afficher la liste des incidents.
	 */
	private IncidentAdapter incidentAdapter;

	/**
	 * The NestedScrollView is used in the input_fragment layout to allow the user to scroll
	 * through the report. Needed as variable to go back to top when a report has been saved. <br>
	 * La NestedScrollView est utilisée dans le layout input_fragment pour permettre à
	 * l'utilisateur de faire défiler l'écran à travers le rapport. Nécessaire en tant que
	 * variable pour retourner au haut de l'écran quand un rapport a été enregistré.
	 */
	private NestedScrollView scrollView;

	/**
	 * layoutInput is the layout containing the save and clear buttons. Needed as a variable cause
	 * the buttons displayed varie according to the state of the report (creation or edition). <br>
	 * layoutInput est le layout contenant les boutons enregistrer et effacer. Nécessaires comme
	 * variable car les boutons affichés dépendent de l'état du rapport (création ou modification).
	 */
	private LinearLayout layoutInput;

	/**
	 * layoutEdit is the layout containing the edit and cancel buttons. <br>
	 * layoutEdit est le layout contenant les boutons modifier et annuler.
	 */
	private LinearLayout layoutEdit;

	/**
	 * reportToEdit the report to edit used when a user edit an existing report. <br>
	 * reportToEdit le rapport à modifier utilisé quand un utilisateur modifie un rapport existant.
	 */
	private Report reportToEdit;

	/**
	 * Class constructor. Not used.
	 * Constructeur de la classe. Non utilisé.
	 */
	public InputFragment() {
		// Required empty public constructor
	}

	/**
	 * onCreateView is called at the creation of the application. It gets all the layout elements
	 * and initialize the variables. <br>
	 * onCreateView est appelée à la création de l'application. Il récupère tous les éléments du
	 * layout et initialise les variables.
	 * @param inflater The inflater, needed to associated the xml file to the view. <br> L'inflater,
	 *                    nécessaire pour associer le fichier xml à la vue.
	 * @param container The container, the view's parent. <br> Le parent de la vue.
	 * @param savedInstanceState The saved state, we won't use it. <br> On ne l'utilisera pas.
	 * @return The input view. <br> La vue de saisie de rapport.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		// Récupération de la vue en cours
		inputView = inflater.inflate(R.layout.fragment_input, container, false);

		// Get all the element from the layout (fragment_input.xml)
		// Récupère les éléments du layout
		/* Address bloc */
		spinTeam = inputView.findViewById(R.id.spinTeam);
		spinUser = inputView.findViewById(R.id.spinUser);
		spinZone = inputView.findViewById(R.id.spinZone);
		spinBuild = inputView.findViewById(R.id.spinBuild);
		spinLvl = inputView.findViewById(R.id.spinLvl);
		spinCanton = inputView.findViewById(R.id.spinCanton);
		spinSpaceCat = inputView.findViewById(R.id.spinSpaceCat);
		spinSpace = inputView.findViewById(R.id.spinSpace);

		/* Maintenance bloc */
		spinNature = inputView.findViewById(R.id.spinNature);
		spinFrequency = inputView.findViewById(R.id.spinFrequency);
		spinMaterial = inputView.findViewById(R.id.spinMaterial);
		etDateStart = inputView.findViewById(R.id.etDateStart);
		etDateEnd = inputView.findViewById(R.id.etDateEnd);
		etTimeStart = inputView.findViewById(R.id.etTimeStart);
		etTimeEnd = inputView.findViewById(R.id.etTimeEnd);

		/* The lists displayed */
		listServOne = inputView.findViewById(R.id.lvService1);
		listServTwo = inputView.findViewById(R.id.lvService2);
		listInters = inputView.findViewById(R.id.lvIntervenants);
		listIncident = inputView.findViewById(R.id.lvIncidents);

		/* The edit text objects of the layout */
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

		// The initialization of all the elements are dispatched in multiple methods
		// L'initialisation de tous les éléments est réalisée dans plusieurs méthodes
		if (mainAct.isDataImported) {
			initializeElements();
			initializeText();
			initializeAddress();
			initializeMaintenance();
			initializeService();
			initializeInters();
			initializeIncident(null);
			initializeDetails();

			// The class implements onItemClickListener so override the onClick method. When a
			// report is touched in the reports list tab, it's displayed in the input tab.
			// La classe implémente onItemClickListener donc override la méthode onClick. Quand
			// un rapport est touché dans l'onglet liste des rapports, il est affiché dans
			// l'onglet saisie de rapport.
			mainAct.reportAdapter.setClickListener(this);
		}

		// We give the reference of this class to the MainActivity
		// On donne la référence de cette classe à MainActivity
		mainAct.setInputFragment(this);

		// The view
		return inputView;
	}

	/**
	 * Initialization of the buttons click listener. <br>
	 * Initialisation des écouteurs des boutons (actions qu'a le bouton quand on click dessus).
	 */
	private void initializeElements() {
		/*
		When a user touch the clear button, a confirmation is asked and the report input fields
		are cleared.
		Quand un utilisateur touche le bouton effacer, une confirmation lui est demandé et les
		champs de saisis du rapport sont effacés.
		 */
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

		/*
		 The report is saved if all the obligatory fields are completed (Nature, start date, start
		 time).
		 Le rapport est enregistré si tous les champs obligatoires sont complétés (Nature, date
		 de début, heure de début).
		 */
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (checkFields())
					save(null);
			}
		});

		/*
		The report is updated when the user click on edit.
		Le rapport est mis à jour quand l'utilisateur click sur modifier.
		 */
		btnSaveEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				save(reportToEdit);
				cancel();
			}
		});

		/*
		The edition is cancelled and the screen displays the reports list.
		La modification est annulée et l'écran affiche l'onglet de la liste des rapports.
		 */
		btnCancelEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				cancel();
				mainAct.changeTab(0);
			}
		});

		/*
		When the mainAct variable needClear change of value, it means that the report input
		fields need to be cleared.
		Quand la variable needClear de mainAct change de valeur, ça signifie que les champs de
		saisie de rapport doivent être effacés.
		 */
		mainAct.needClear.setListener(new BooleanVariable.ChangeListener() {
			@Override
			public void onChange() {
				clear();
			}
		});
	}

	/**
	 * Initialize the address "bloc". The spinners are all filled and associated to their listener. <br>
	 * Initialise le "bloc" adresse. Les listes déroulantes sont toutes remplies et associées à
	 * leur écouteur.
	 */
	private void initializeAddress() {
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


		/*
		 The space category spinner and space spinner can be not displayed according to the osgrim
		 data.
		 La liste déroulante de catégorie de local et de local peuvent ne pas être affichés
		 selon les données récupérées d'Osgrim.
		 */
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

	/**
	 * Initialize the elements of the maintenance bloc. Fill the spinners, set the listeners and
	 * configure the date and time input. <br>
	 * Initialise les éléments du bloc maintenance. Remplie les listes déroulantes, associe les
	 * écouteurs et configure le saisies de date et d'heure
	 */
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

		ArrayAdapter<Frequency> freqAdapter = new ArrayAdapter<Frequency>(mainAct, R.layout.support_simple_spinner_dropdown_item, mainAct.frequencies) {
			@Override
			public int getCount() {
				return mainAct.frequencies.size() - 1;
			}
		};

		spinFrequency.setAdapter(freqAdapter);
		spinFrequency.setSelection(mainAct.frequencies.size() - 1);

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

		// When the user touch the edit text to choose a start date, a DatePickerDialog opens and
		// when the user submit the date is displayed in the edit text field.
		// Quand l'utilisateur touche la zone de texte pour choisir un date de début, un
		// DatePickerDialog s'ouvre et quand l'utilisateur valide la date est affichée dans le
		// champ de saisie.
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

		/*
		When the start time EditText is touched, a TimePickerDialog opens with the current time
		and the edit text displays the time chosen by the user with the hh:mm format.
		Quand la zone de saisie de l'heure de début est touchée, un TimePickerDialog s'ouvre avec
		l'heure actuelle et la zone de saisie affiche l'heure choisir par l'utilisateur dans le
		format hh:mm.
		 */
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

	/**
	 * Fill the EditText in parameter with the value of myCalendar with the format dd/mm/yy. <br>
	 * Remplie la zone de saisie de texte en paramètre avec la valeur de myCalendar sous le
	 * format jj/mm/aa.
	 * @param et The EditText to fill. <br> L'EditText à remplir.
	 */
	private void updateLabel(EditText et) {
		String format = "dd/MM/yy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.FRANCE);
		et.setText(sdf.format(myCalendar.getTime()));
	}

	/**
	 * Displays the list of services on the RecyclerView listServ(One/Two) for the two categories of
	 * service. <br>
	 * Affiche la liste des services sur le RecyclerView listServ(One/Two) pour les deux
	 * catégories de service.
	 */
	private void initializeService() {
		List<Service> servicesOne, servicesTwo;
		servicesOne = mainAct.serviceCats.get(0).getServices();
		servicesTwo = mainAct.serviceCats.get(1).getServices();

		servAdapterOne = new ServiceAdapter(getContext(), servicesOne);
		servAdapterTwo = new ServiceAdapter(getContext(), servicesTwo);

		/*
		By setting the adapter, the recyclerView will be filled with the list of services and a
		checkbox associated to each service.
		En associant l'adaptateur, la recyclerView sera remplie avec la liste des services et une
		 case à cocher associée à chaque service.
		 */
		listServOne.setAdapter(servAdapterOne);
		listServTwo.setAdapter(servAdapterTwo);
	}

	/**
	 * Displays the list of intervenants (contributors) in the recyclerView listInters
	 * corresponding. <br>
	 * Affiche la liste des intervenants dans le recyclerView listInters correspondant.
	 */
	private void initializeInters() {
		List<Intervenant> inters = new ArrayList<>();

		/*
		We use an intervenant object because every team members must be displayed so some user
		will be displayed several times with a different team.
		On utilise un objet Intervenant car tous les membres d'équipe doivent être affichés donc
		quelques utilisateurs seront affichés plusieurs fois avec des équipes différents.
		 */
		for (Team team : mainAct.teams)
			for (User user : team.getMembers())
				inters.add(new Intervenant(team, user));

		interAdapter = new IntervenantAdapter(getContext(), inters);
		listInters.setAdapter(interAdapter);
	}

	/**
	 * Displays the incidents checkbox list. <br>
	 * Affiche la liste de case à cocher d'incidents.
	 * @param material The incidents displayed depends on the selected material in parameter. <br> Les
	 *                   incidents affichés dépendent du matériel sélectionné en paramètre.
	 */
	private void initializeIncident(Material material) {
		List<Incident> incidents = (material != null) ? material.getIncidents() : null;

		// If there are incidents associated to the materiel then we display them.
		// S'il y a des incidents associés au matériel alors on les affiche.
		if (incidents != null) {
			fillIncident(incidents);
		}
	}

	/**
	 * Set the adapter according to the list of incidents. <br>
	 * Définit l'adaptateur selon la liste des incidents.
	 * @param incidents The incidents to display. <br> Les incidents à afficher.
	 */
	private void fillIncident(List<Incident> incidents) {
		incidentAdapter = new IncidentAdapter(getContext(), incidents);
		listIncident.setAdapter(incidentAdapter);
	}

	/**
	 * Fill the details spinners according to the number of details (from 1 to 6). <br>
	 * Remplie les listes déroulantes des détails selon le nombre de détails (de 1 à 6).
	 */
	private void initializeDetails() {
		List<Detail> details = mainAct.details;
		detailSpinners = new Spinner[details.size()];

		// Configuration of each spinner the number of details size.
		// Configuration de chaque liste de déroulante fois le nombre de détails.
		for (int i = 0; i < details.size(); i++) {
			int tvID = getResources().getIdentifier("txtDetail" + (i + 1), "id", mainAct.getPackageName());
			int spinID = getResources().getIdentifier("spinDetail" + (i + 1), "id", mainAct.getPackageName());

			TextView txtDetail = inputView.findViewById(tvID);
			Spinner spinDetail = inputView.findViewById(spinID);

			txtDetail.setText(details.get(i).getTitle());
			List<String> answers = details.get(i).getAnswers();

			final int answersSize = answers.size() - 1;

			ArrayAdapter<String> detailAdapter = new ArrayAdapter<String>(mainAct, R.layout.support_simple_spinner_dropdown_item, answers) {
				@Override
				public int getCount() {
					return answersSize;
				}
			};

			spinDetail.setAdapter(detailAdapter);

			spinDetail.setSelection(answersSize);

			// The spinner is visible but the others are hidden (if there is less than 6 details).
			// La liste déroulante est visible mais les autres sont caché (s'il y a moins de 6
			// détails).
			spinDetail.setVisibility(View.VISIBLE);
			detailSpinners[i] = spinDetail;
		}

		LinearLayout layoutDetail = inputView.findViewById(R.id.layoutDetail);
		layoutDetail.setVisibility(View.VISIBLE);
	}

	/**
	 * When the user saves a report, some fields must be filled and if they are not, an error
	 * message will alert the user. <br>
	 * Quand l'utilisateur enregistre un rapport, certains champs doivent être remplis et s'ils
	 * ne le sont pas, un message d'erreur préviendra l'utilisateur.
	 * @return True if the fields are well filled, the report'll be save. False if some fields
	 * are not or not correctly filled. <br> Vrai si les champs sont bien remplis, le rapport sera
	 * enregistré. Faux si certains champs ne sont pas ou non correctement remplis.
	 */
	private boolean checkFields() {
		// The nature field is obligatory.
		// Le champ nature est obligatoire.
		Nature nature = (Nature) spinNature.getSelectedItem();

		// If nothing is selected the id is -1.
		// Si rien n'est sélectionné l'id est -1.
		if (nature.getId() == -1) {
			// Make an alert popup. // Fait une popup d'alerte.
			makeAlert(mainAct.messages.get("natureMissing"));

			// The focus is on the spinner to let the user see it easily.
			// Le focus est sur la liste déroulante pour que l'utilisateur voit le problème
			// facilement.
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

		/*
		The start date and start time are obligatory.
		La date de début et l'heure de début sont obligatoires.
		 */
		if (strStartDate.length() == 0) {
			// Alert popup and focus on the start date field
			// Popup d'alerte et focus sur le champ de date de début
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

		/*
		If there are an end date we must check that it is greater than the start date.
		S'il y a une date de fin on doit vérifier qu'elle est supérieure à la date de début.
		 */
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

	/**
	 * Gives the focus to the edittext given in parameter. <br>
	 * Donne le focus à la zone de texte donnée en paramètre.
	 * @param et The EditText that'll have the focus. <br> La zone de texte qui aura le focus.
	 */
	private void focusEditText(EditText et) {
		et.setFocusable(true);
		et.setFocusableInTouchMode(true);
		et.requestFocus();
		et.setFocusableInTouchMode(false);
		et.setFocusable(false);
	}

	/**
	 * Makes an alert popup with the message in parameter. <br>
	 * Fait une popup d'alerte avec le message en paramètre.
	 * @param msg The message to display in the popup. <br> Le message à afficher dans la popup.
	 */
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

		// Configuration of the layout to have the message and the button centered.
		// Configuration du layout pour avoir le message et le bouton centré.
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.weight = Float.parseFloat("1");
		layoutParams.gravity = Gravity.CENTER;
		alert.show();
		TextView messageView = alert.findViewById(android.R.id.message);
		messageView.setGravity(Gravity.CENTER);
		alert.getButton(AlertDialog.BUTTON_POSITIVE).setLayoutParams(layoutParams);
	}

	/**
	 * Saves the inputted report in the list of reports. <br>
	 * Enregistre le rapport saisi dans la liste des rapports.
	 * @param repToSave Null if it's a report creation, or a Report object if it's an edition.
	 *                     <br> Null si c'est une création de rapport, ou un objet Report si
	 *                     c'est une modification.
	 */
	@SuppressLint("UseSparseArrays")
	private void save(Report repToSave) {
		/*
		Get all the inputted elements.
		Récupération de tous les éléments saisis.
		 */
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

		/*
		Get all the list elements selected.
		Récupération de tous les éléments de listes sélectionnés.
		 */
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

		/*
		If it's a report creation, a new Report object is declared. If not, the Report object is
		updated.
		Si c'est une création de rapport, un nouvel objet Report est déclaré. Sinon, l'objet
		Report est mis à jour.
		 */
		if (repToSave == null) {
			repToSave = new Report(nature, dateStart, timeStart, currentDate);
			mainAct.reports.add(repToSave);
		} else {
			repToSave.setNature(nature);
			repToSave.setStartDate(dateStart);
			repToSave.setStartTime(timeStart);
		}

		/*
		If the spinner elements are selected, they are indicated in the report object.
		Si les éléments de spinner sont sélectionnés, ils sont indiqués dans l'objet rapport.
		 */
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

		/*
		Useless to check for the EditText, if it's empty then the report will have an empty
		element (and not null).
		Inutile de vérifier pour les zones de texte, si c'est vide alors le rapport aura un
		élément vide (et non null).
		 */
		repToSave.setPillar(pillar);
		repToSave.setCode(code);
		repToSave.setEndDate(dateEnd);
		repToSave.setEndTime(timeEnd);
		repToSave.setComment(comment);

		repToSave.setServices(serviceCatList);
		repToSave.setIntervenants(intervenants);
		repToSave.setIncidents(incidents);
		repToSave.setDetails(details);

		/*
		 Notification to the reportAdapter so it displays a new report on the reports list (on the
		 first tab).
		 Notification à l'adaptateur de rapport afin qu'il affiche un nouveau rapport dans la
		 liste des rapports (dans le premier onglet).
		 */
		mainAct.reportAdapter.notifyDataSetChanged();

		// Indication that the report has been saved.
		// Indication indiquant que le rapport a été enregistré.
		Toast.makeText(mainAct, mainAct.messages.get("saveComplete"), Toast.LENGTH_LONG).show();

		// The report input fields are cleared and ready to create a new report.
		// Les champs de la saisie de rapport sont effacés et prêts à créer un nouveau rapport.
		clear();
	}

	/**
	 * Clears all the fields of the report input and scrolls to the top screen. <br>
	 * Efface tous les champs de saisie de rapport et défile l'écran en haut.
	 */
	@SuppressLint("ClickableViewAccessibility")
	void clear() {
		/*
		All the spinners display a blank item.
		Toutes les listes déroulantes affichent un élément vide.
		 */
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

		/*
		All the checked elements are unchecked.
		Tous les éléments sélectionnés sont désélectionnés.
		 */
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
				incidentAdapter.setDeselected(i);
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

	/**
	 * Displays the report given in parameter. Used when a user wants to edit a report by
	 * touching a report in the list of reports. <br>
	 * Affiche le rapport donné en paramètre. Utilisé quand un utilisateur veut modifier un
	 * rapport en le touchant dans la liste des rapports.
	 * @param report The report to display. <br> Le rapport à afficher.
	 */
	private void displayReport(Report report) {
		// The report reference is needed to save the report when the user click on the edit button.
		// La référence du rapport est nécessaire afin d'enregistrer le rapport quand
		// l'utilisateur clique sur le bouton de modification de rapport.
		reportToEdit = report;

		/*
		We hide the save and clear buttons and show the edit and cancel buttons.
		On cache les boutons enregistrer et effacer et montre les boutons modifier et annuler.
		 */
		layoutInput.setVisibility(View.INVISIBLE);
		layoutEdit.setVisibility(View.VISIBLE);

		/*
		Select the values saved in the report for the different spinners.
		Sélection des valeurs enregistrées dans le rapport pour les différentes listes déroulantes.
		 */
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

		// Set the text of the edittext with the report values.
		// Mettre en valeur des zones de textes les valeurs du rapport.
		etPillar.setText(report.getPillar());
		etCode.setText(report.getCode());
		etDateStart.setText(report.getStartDate());
		etTimeStart.setText(report.getStartTime());
		etDateEnd.setText(report.getEndDate());
		etTimeEnd.setText(report.getEndTime());
		etComment.setText(report.getComment());

		/*
		Select the services saved in the report.
		Sélection des services enregistrés dans le rapport.
		 */
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

		/*
		Select the contributors saved in the report.
		Sélection des intervenants enregistrés dans le rapport.
		 */
		List<Intervenant> interSelected = report.getIntervenants();
		for (int i = 0; i < interAdapter.getItemCount(); i++) {
			for (Intervenant inter : interSelected) {
				if (inter.getTeam().getId() == interAdapter.getItem(i).getTeam().getId() && inter.getUser().getId() == interAdapter.getItem(i).getUser().getId())
					interAdapter.setSelected(i, true);
			}
		}

		List<Incident> allIncidents = (report.getMaterial() != null) ? report.getMaterial().getIncidents() : new ArrayList<Incident>();
		List<Incident> incidentSelected = report.getIncidents();

		/*
		Select the incidents selected in the report.
		Sélection des incidents sélectionnés dans le rapport.
		 */
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

		// The focus is on the report input tab.
		// Le focus est sur l'onglet de saisie de rapport.
		mainAct.changeTab(1);
	}

	/**
	 * Set the selection to the Object value for the spinner in parameter. <br>
	 * Sélectionne l'objet value pour la liste déroulante spinner.
	 * @param spinner The spinner to set the selected value. <br> La liste déroulante pour laquelle on
	 *                  sélectionne une valeur.
	 * @param value The value that has to be selected by the spinner. <br> La valeur qui doit être
	 *                 sélectionnée par la liste déroulante.
	 */
	private void selectValue(Spinner spinner, Object value) {
		if (value == null)
			return;

		/*
		 We check the object name to see if it's the one to select because this method is used for
		 different types of object so the code isn't redundant.
		 On vérifie le nom de l'objet pour voir si c'est celui à sélectionner parce que cette
		 méthode est utilisée pour différents types d'objets afin que le code ne soit pas redondant.
		 */
		for (int i = 0; i < spinner.getCount(); i++) {
			if (value.toString().equals(spinner.getItemAtPosition(i).toString())) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	/**
	 * Canceling of the report edition and displays the classic report input. <br>
	 * Annule la modification de rapport et affiche la saisie classique de rapport.
	 */
	private void cancel() {
		layoutEdit.setVisibility(View.INVISIBLE);
		layoutInput.setVisibility(View.VISIBLE);
		clear();
	}

	/**
	 * Initialize the labels of all the TextView of the view with the mainAct.labels list. <br>
	 * Initialise les libellés de tous les TextView de la vue avec la liste mainAct.labels.
	 */
	private void initializeText() {
		/*
		Get all the TextView elements of the layout.
		Récupération de tous les éléments TextView du layout.
		 */
		TextView txtTeam, txtUser, txtAddress, txtZone, txtBuild, txtLvl, txtCanton, txtPillar, txtSpaceCat, txtSpace;
		TextView txtMaintenance, txtNature, txtFrequency, txtMaterial, txtCode, txtStartDate, txtEndDate, txtStartDate2, txtEndDate2, txtStartTime, txtEndTime;
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
		txtStartDate2 = inputView.findViewById(R.id.txtStartDate2);
		txtEndDate2 = inputView.findViewById(R.id.txtEndDate2);
		txtStartTime = inputView.findViewById(R.id.txtStartTime);
		txtEndTime = inputView.findViewById(R.id.txtEndTime);
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

		/*
		Association of the text on the TextView elements.
		Association du texte sur les éléments TextView.
		 */
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
		txtServices.setText(mainAct.labels.get("services"));
		txtIntervenants.setText(mainAct.labels.get("participant"));
		txtInters.setText(mainAct.labels.get("participant"));
		txtTeams.setText(mainAct.labels.get("teams"));
		txtIncident.setText(mainAct.labels.get("incident"));
		txtIncidents.setText(mainAct.labels.get("incident"));
		txtComment.setText(mainAct.labels.get("comment"));
		txtDetails.setText(mainAct.labels.get("details"));

		/*
		  On tablet, the start date and start time are on the same line so it needs only one label.
		  On smartphone, the start date is in a different line than the start time so it needs two
		  labels. We check if we're on tablet or smartphone with the InputFragment tag (defined
		  in input_fragment.xml).
		  Sur tablette, la date de début et l'heure de début ne sont pas sur la même ligne donc
		  il n'y a besoin que d'un libellé. Sur smarphone, la date de début est sur une ligne
		  différente que l'heure du début donc il faut deux labels. On vérifie si on est sur
		  tablette ou smartphone avec le tag de InputFragment (défini dans input_fragment.xml).
		 */
		if (!getTagStr().equals("smartphone")) {
			txtStartDate.setText(mainAct.labels.get("startDate"));
			txtEndDate.setText(mainAct.labels.get("endDate"));
		} else {
			txtStartDate2.setText(mainAct.labels.get("startDate2"));
			txtEndDate2.setText(mainAct.labels.get("endDate2"));
			txtStartTime.setText(mainAct.labels.get("startTime"));
			txtEndTime.setText(mainAct.labels.get("endTime"));
		}

		btnSave.setText(mainAct.labels.get("save"));
		btnClear.setText(mainAct.messages.get("clear"));

		btnSaveEdit.setText(mainAct.messages.get("editReport"));
		btnCancelEdit.setText(mainAct.messages.get("cancel"));

		txtService1.setText(mainAct.serviceCats.get(0).getName());
		txtService2.setText(mainAct.serviceCats.get(1).getName());
	}

	/**
	 * Get the input fragment tag and returns it in string type. <br>
	 * Récupère le tag du fragment de saisie du rapport et le retourne en type string.
	 * @return The tag of this view in string. <br> Le tag de cette vue en string.
	 */
	String getTagStr() {
		return inputView.getTag().toString();
	}

	/**
	 * This method had to be override because it avoids to execute the onCreate() method when the
	 * device is rotated. The user can rotate the device and nothing will change about the data
	 * he entered. <br>
	 * Cette methode devait être override car ça évite d'exécuter la méthode onCreate() quand
	 * l'appareil est tourné. L'utilisateur peut tourner l'appareil et rien ne changera à propos
	 * des données qu'il a déjà saisi.
	 * @param config The Configuration.
	 */
	@Override
	public void onConfigurationChanged(@NonNull Configuration config) {
		super.onConfigurationChanged(config);
	}

	/**
	 * onClick which is executed when an element in the reports list has been touched (in the
	 * reports list tab). <br>
	 * onClick qui est exécuté quand un éléments dans la liste des rapports a été touché (dans
	 * l'onglet liste des rapports).
	 * @param view The concerned view. <br> La vue concernée.
	 * @param position The position of the report in the list of reports. <br> La position du rapport
	 *                    dans la liste des rapports.
	 */
	@Override
	public void onClick(View view, int position) {
		// The onClick implementation of the RecyclerView item click
		Report report = mainAct.reports.get(position);
		clear();
		displayReport(report);
	}

	/**
	 * Fill the user spinner according to the team in parameter, which is the team that has been
	 * selected. <br>
	 * Remplie la liste déroulante des utilisateurs selon l'équipe en paramètre, qui est l'équipe
	 * qui a été sélectionnée.
	 * @param team The selected team. <br> L'équipe sélectionnée.
	 */
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

	/**
	 * Fill the building spinner according to the zone in parameter, which is the zone that has
	 * been selected. <br>
	 * Remplie la liste déroulante des bâtiments selon la zone en paramètre, qui est la zone qui
	 * a été sélectionnée.
	 * @param zone The selected zone. <br> La zone sélectionnée.
	 */
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

	/**
	 * Fill the level spinner according to the building in parameter, which is the building that
	 * has been selected. <br>
	 * Remplie la liste déroulante des niveaux selon le bâtiment en paramètre, qui est le
	 * bâtiment qui a été sélectionné.
	 * @param building The selected building. <br> Le bâtiment sélectionné.
	 */
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

	/**
	 * Fill the space spinner according to the space category in parameter, which is the space
	 * category that has been selected. <br>
	 * Remplie la liste déroulante des locaux selon la catégorie de local en paramètre, qui est
	 * la catégorie de local qui a été sélectionnée.
	 * @param spaceCat The selected space categorie. <br> La catégorie de local sélectionnée.
	 */
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

	/**
	 * Class used when the user touches a team spinner. When he selects a team, the user spinner is
	 * filled according to the selected team. <br>
	 * Classe utilisée quand l'utilisateur touche la liste déroulante des équipes. Quand il
	 * sélectionne une équipe, la liste déroulante des utilisateurs se remplie selon l'équipe
	 * sélectionnée.
	 */
	private class SpinnerTeamListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		/**
		 * We make sure the spinner is "opening" and if it is touched is true to use it later. <br>
		 * On s'assure que la liste s'ouvre et si c'est le cas touched est vrai pour l'utiliser
		 * plus tard.
		 * @param view The view.
		 * @param motionEvent The movement done. <br> Le mouvement réalisé.
		 * @return Always false, doesn't matter for our situation. <br> Toujours faux, ça n'a pas
		 * d'importance dans notre situation.
		 */
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		/**
		 * When a team is selected and we're sure that's the user who selected it (with touched),
		 * the user spinner is filled according to the team. <br>
		 * Quand une équipe est sélectionnée et qu'on est sûr que c'est l'utilisateur qui l'a
		 * sélectionnée (avec touched), la liste déroulante des utilisateurs est remplie selon
		 * l'équipe sélectionnée.
		 * @param adapterView The AdapterView where the selection happen. <br> L'AdapterView où se
		 *                          passe la sélection.
		 * @param view The view within the AdapterView that was clicked. <br> La vue dans
		 *                   l'AdapterView qui a été clické.
		 * @param i The position of the view in the adapter. <br> La position de la vue dans
		 *                l'adaptateur.
		 * @param l The row id of the item that is selected. <br> L'id de la ligne de l'item
		 *                sélectionné.
		 */
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

	/**
	 * Class used when the user touches a zone spinner. When he selects a zone, the building
	 * spinner is filled according to the selected zone. <br>
	 * Classe utilisée quand l'utilisateur touche la liste déroulante des zones. Quand il
	 * sélectionne une zone, la liste déroulantes des bâtiments se remplie selon la zone
	 * sélectionnée.
	 */
	private class SpinnerZoneListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		/**
		 * We make sure the spinner is "opening" and if it is touched is true to use it later. <br>
		 * On s'assure que la liste s'ouvre et si c'est le cas touched est vrai pour l'utiliser
		 * plus tard.
		 * @param view The view.
		 * @param motionEvent The movement done. <br> Le mouvement réalisé.
		 * @return Always false, doesn't matter for our situation. <br> Toujours faux, ça n'a pas
		 * d'importance dans notre situation.
		 */
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		/**
		 * When a zone is selected and we're sure that's the user who selected it (with touched),
		 * the building spinner is filled according to the zone. <br>
		 * Quand une zone est sélectionnée et qu'on est sûr que c'est l'utilisateur qui l'a
		 * sélectionnée (avec touched), la liste déroulante des bâtiments est remplie selon
		 * la zone sélectionnée.
		 * @param adapterView The AdapterView where the selection happen. <br> L'AdapterView où se
		 *                          passe la sélection.
		 * @param view The view within the AdapterView that was clicked. <br> La vue dans
		 *                   l'AdapterView qui a été clické.
		 * @param i The position of the view in the adapter. <br> La position de la vue dans
		 *                l'adaptateur.
		 * @param l The row id of the item that is selected. <br> L'id de la ligne de l'item
		 *                sélectionné.
		 */
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

	/**
	 * Class used when the user touches a building spinner. When he selects a building, the levels
	 * spinner is filled according to the selected building. <br>
	 * Classe utilisée quand l'utilisateur touche la liste déroulante des bâtiments. Quand il
	 * sélectionne un bâtiment, la liste déroulantes des niveaux se remplie selon le bâtiment
	 * sélectionné.
	 */
	private class SpinnerBuildingListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		/**
		 * We make sure the spinner is "opening" and if it is touched is true to use it later. <br>
		 * On s'assure que la liste s'ouvre et si c'est le cas touched est vrai pour l'utiliser
		 * plus tard.
		 * @param view The view.
		 * @param motionEvent The movement done. <br> Le mouvement réalisé.
		 * @return Always false, doesn't matter for our situation. <br> Toujours faux, ça n'a pas
		 * d'importance dans notre situation.
		 */
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		/**
		 * When a building is selected and we're sure that's the user who selected it (with
		 * touched), the levels spinner is filled according to the zone. <br>
		 * Quand un bâtiment est sélectionné et qu'on est sûr que c'est l'utilisateur qui l'a
		 * sélectionné (avec touched), la liste déroulante des niveaux est remplie selon
		 * le bâtiment sélectionné.
		 * @param adapterView The AdapterView where the selection happen. <br> L'AdapterView où se
		 *                          passe la sélection.
		 * @param view The view within the AdapterView that was clicked. <br> La vue dans
		 *                   l'AdapterView qui a été clické.
		 * @param i The position of the view in the adapter. <br> La position de la vue dans
		 *                l'adaptateur.
		 * @param l The row id of the item that is selected. <br> L'id de la ligne de l'item
		 *                sélectionné.
		 */
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

	/**
	 * Class used when the user touch a space categories spinner. When he selects a space category,
	 * the spaces spinner is filled according to the selected space category. <br>
	 * Classe utilisée quand l'utilisateur touche la liste déroulante des catégories de local.
	 * Quand il sélectionne une catégorie de local, la liste déroulantes des objets local se
	 * remplie selon la catégorie de local sélectionnée.
	 */
	private class SpinnerSpaceCatListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		/**
		 * We make sure the spinner is "opening" and if it is touched is true to use it later. <br>
		 * On s'assure que la liste s'ouvre et si c'est le cas touched est vrai pour l'utiliser
		 * plus tard.
		 * @param view The view.
		 * @param motionEvent The movement done. <br> Le mouvement réalisé.
		 * @return Always false, doesn't matter for our situation. <br> Toujours faux, ça n'a pas
		 * d'importance dans notre situation.
		 */
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		/**
		 * When a space category is selected and we're sure that's the user who selected it (with
		 * touched), the spaces spinner is filled according to the space category. <br>
		 * Quand une catégorie de local est sélectionnée et qu'on est sûr que c'est l'utilisateur
		 * qui l'a sélectionnée (avec touched), la liste déroulante des objets local est remplie
		 * selon la catégorie de local sélectionnée.
		 * @param adapterView The AdapterView where the selection happen. <br> L'AdapterView où se
		 *                          passe la sélection.
		 * @param view The view within the AdapterView that was clicked. <br> La vue dans
		 *                   l'AdapterView qui a été clické.
		 * @param i The position of the view in the adapter. <br> La position de la vue dans
		 *                l'adaptateur.
		 * @param l The row id of the item that is selected. <br> L'id de la ligne de l'item
		 *                sélectionné.
		 */
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

	/**
	 * Class used when the user touches a materials spinner. When he selects a material,
	 * the incidents list is filled according to the selected material. <br>
	 * Classe utilisée quand l'utilisateur touche la liste déroulante des matériels.
	 * Quand il sélectionne un matériel, la liste des incidents se
	 * remplie selon le matériel sélectionné.
	 */
	private class SpinnerMaterialListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

		boolean touched = false;

		/**
		 * We make sure the spinner is "opening" and if it is touched is true to use it later. <br>
		 * On s'assure que la liste s'ouvre et si c'est le cas touched est vrai pour l'utiliser
		 * plus tard.
		 * @param view The view.
		 * @param motionEvent The movement done. <br> Le mouvement réalisé.
		 * @return Always false, doesn't matter for our situation. <br> Toujours faux, ça n'a pas
		 * d'importance dans notre situation.
		 */
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
				touched = true;
			else if (motionEvent.getAction() == MotionEvent.ACTION_UP)
				view.performClick();
			return false;
		}

		/**
		 * When a material is selected and we're sure that's the user who selected it (with
		 * touched), the incidents list is filled according to the material. <br>
		 * Quand un matériel est sélectionné et qu'on est sûr que c'est l'utilisateur
		 * qui l'a sélectionné (avec touched), la liste des incidents est remplie
		 * selon le matériel sélectionné.
		 * @param adapterView The AdapterView where the selection happen. <br> L'AdapterView où se
		 *                          passe la sélection.
		 * @param view The view within the AdapterView that was clicked. <br> La vue dans
		 *                   l'AdapterView qui a été clické.
		 * @param i The position of the view in the adapter. <br> La position de la vue dans
		 *                l'adaptateur.
		 * @param l The row id of the item that is selected. <br> L'id de la ligne de l'item
		 *                sélectionné.
		 */
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
