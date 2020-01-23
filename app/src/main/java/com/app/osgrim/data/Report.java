package com.app.osgrim.data;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Report class defines a report. <br>
 * La classe Report (Rapport) définie un rapport.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class Report {

	/**
	 * The report selected team. <br>
	 * L'équipe sélectionnée du rapport.
	 */
	private Team team;

	/**
	 * The report selected user. <br>
	 * L'utilisateur sélectionné du rapport.
	 */
	private User user;

	/**
	 * The report selected zone. <br>
	 * La zone sélectionnée du rapport.
	 */
	private Zone zone;

	/**
	 * The report selected building. <br>
	 * Le bâtiment sélectionné du rapport.
	 */
	private Building building;

	/**
	 * The report selected level. <br>
	 * Le niveau sélectionné du rapport.
	 */
	private Level level;

	/**
	 * The report selected canton. <br>
	 * Le canton sélectionné du rapport.
	 */
	private Canton canton;

	/**
	 * The report selected space category. <br>
	 * La catégorie de local sélectionnée du rapport.
	 */
	private SpaceCat spaceCat;

	/**
	 * The report selected space. <br>
	 * Le local sélectionné du rapport.
	 */
	private Space space;

	/**
	 * The report selected nature. <br>
	 * La nature sélectionnée du rapport.
	 */
	private Nature nature;

	/**
	 * The report selected frequency. <br>
	 * La périodicité sélectionnée du rapport.
	 */
	private Frequency frequency;

	/**
	 * The report selcted material. <br>
	 * Le matériel sélectionné du rapport.
	 */
	private Material material;

	/**
	 * The services lists by service category ids selected in the reports. <br>
	 * La liste des services par identifiants de catégorie de service sélectionnés dans le rapport.
	 */
	private Map<Integer, List<Service>> services;

	/**
	 * The report selected contributors. <br>
	 * Les intervenants sélectionnés du rapport.
	 */
	private List<Intervenant> intervenants;

	/**
	 * The report details with the title and its answer. <br>
	 * Les détails du rapport avec le titre et sa réponse.
	 */
	private Map<String, String> details;

	/**
	 * The report selected incidents. <br>
	 * Les incidents sélectionnés du rapport.
	 */
	private List<Incident> incidents;

	/**
	 * The report input fields. startDate, startTime and dateSave are the only one that can't be
	 * empty
	 * string. <br>
	 * Les champs de saisie du rapport. startDate, startTime et dateSave sont les seuls qui ne
	 * peuvent pas
	 * être des chaînes de caractères vides.
	 */
	private String pillar, code, startDate, startTime, endDate, endTime, comment, dateSave;

	/**
	 * Creates a Report object. A report must have an action nature, a start date, a start time,
	 * and a save date. <br>
	 * Créé un objet Report. Un rapport doit avoir une nature d'action, une date
	 * de début, une heure de début et une date d'enregistrement.
	 * @param nature The action nature of the report. <br> La nature de l'action du rapport.
	 * @param startDate The start date of the report's action. <br> La date de début de l'action du
	 *                     rapport.
	 * @param startTime The start time of the report's action. <br> L'heure de début de l'action du
	 *                     rapport.
	 * @param dateSave The save date of the report. <br> La date d'enregistrement du rapport.
	 */
	@SuppressLint("UseSparseArrays")
	public Report(Nature nature, String startDate, String startTime, String dateSave) {
		this.nature = nature;
		this.startDate = startDate;
		this.startTime = startTime;
		this.dateSave = dateSave;
		// Initialization of the other report's elements.
		team = null;
		zone = null;
		user = null;
		building = null;
		level = null;
		canton = null;
		spaceCat = null;
		space = null;
		frequency = null;
		material = null;
		services = new HashMap<>();
		intervenants = new ArrayList<>();
		incidents = new ArrayList<>();
		details = new LinkedHashMap<>();
	}

	/**
	 * Get the team chosen in the report. <br> Renvoie l'équipe choisie dans le rapport.
	 * @return The selected team. <br> L'équipe sélectionnée.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Set the selected team of the report. <br> Fixe l'équipe sélectionnée du rapport.
	 * @param team The team selected in the report. <br> L'équipe sélectionnée dans le rapport.
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Get the selected user of the report. <br> Renvoie l'utilisateur sélectionné du rapport.
	 * @return The selected user. <br> L'utilisateur sélectionné.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the selected user of the report. <br> Fixe l'utilisateur sélectionné du rapport.
	 * @param user The user selected in the report. <br> L'utilisateur sélectionné dans le rapport.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get the selected zone of the report. <br> Renvoie la zone sélectionnée du rapport.
	 * @return The selected zone. <br> La zone sélectionnée.
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * Set the selected zone of the report. <br> Fixe la zone sélectionnée du rapport.
	 * @param zone The zone selected in the report. <br> La zone sélectionnée dans le rapport.
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * Get the selected building of the report. <br> Renvoie le bâtiment sélectionné du rapport.
	 * @return The selected building. <br> Le bâtiment sélectionné.
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Set the selected building of the report. <br> Fixe le bâtiment sélectionné du rapport.
	 * @param building The selected building in the report. <br> Le bâtiment sélectionné dans le
	 *                    rapport.
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * Get the selected level of the report. <br> Renvoie le niveau sélectionné du rapport.
	 * @return The selected level. <br> Le niveau sélectionné.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Set the selected level of the report. <br> Fixe le niveau sélectionné du rapport.
	 * @param level The selected level in the report. <br> Le niveau sélectionné dans le rapport.
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Get the selected canton of the report. <br> Renvoie le canton sélectionné du rapport.
	 * @return The selected canton. <br> Le canton sélectionné.
	 */
	public Canton getCanton() {
		return canton;
	}

	/**
	 * Set the selected canton of the report. <br> Fixe le canton sélectionné du rapport.
	 * @param canton The selected canton in the report. <br> Le canton sélectionné dans le rapport.
	 */
	public void setCanton(Canton canton) {
		this.canton = canton;
	}

	/**
	 * Get the selected space category of the report. <br> Renvoie la catégorie de local sélectionnée du
	 * rapport.
	 * @return The selected space catégorie. <br> La catégorie de local sélectionnée.
	 */
	public SpaceCat getSpaceCat() {
		return spaceCat;
	}

	/**
	 * Set the selected space category of the report. <br> Fixe la catégorie de local sélectionnée du
	 * rapport.
	 * @param spaceCat The selected space category in the report. <br> La catégorie de local
	 *                    sélectionnée dans le rapport.
	 */
	public void setSpaceCat(SpaceCat spaceCat) {
		this.spaceCat = spaceCat;
	}

	/**
	 * Get the selected space of the report. <br> Renvoie le local sélectionné du rapport.
	 * @return The selected space in the report. <br> Le local sélectionné dans le rapport.
	 */
	public Space getSpace() {
		return space;
	}

	/**
	 * Set the selected space of the report. <br> Fixe le local sélectionné du rapport.
	 * @param space The selected space in the report. <br> Le local sélectionné dans le rapport.
	 */
	public void setSpace(Space space) {
		this.space = space;
	}

	/**
	 * Get the selected nature of the report. <br> Renvoie la nature sélectionnée du rapport.
	 * @return The selected nature in the report. <br> La nature sélectionnée dans le rapport.
	 */
	public Nature getNature() {
		return nature;
	}

	/**
	 * Set the selected nature of the report. <br> Fixe la nature sélectionnée du rapport.
	 * @param nature The selected nature in the report. <br> La nature sélectionnée dans le rapport.
	 */
	public void setNature(Nature nature) {
		this.nature = nature;
	}

	/**
	 * Get the selected frequency of the report. <br> Renvoie la périodicité sélectionnée du rapport.
	 * @return The selected frequency in the report. <br> La périodicité sélectionnée dans le rapport.
	 */
	public Frequency getFrequency() {
		return frequency;
	}

	/**
	 * Set the selected frequency of the report. <br> Fixe la périodicité sélectionnée du rapport.
	 * @param frequency The selected frequency in the report. <br> La périodicité sélectionnée dans le
	 *                    rapport.
	 */
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	/**
	 * Get the selected material of the report. <br> Renvoie le matériel sélectionné du rapport.
	 * @return The selected material in the report. <br> Le matériel sélectionné dans le rapport.
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Set the selected material of the report. <br> Fixe le matériel sélectionné du rapport.
	 * @param material The selected material in the report. <br> Le matériel sélectionné dans le
	 *                    rapport.
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Get the list of services selected on the report by service categories. <br> Renvoie la liste
	 * des services sélectionnés sur le rapport par catégorie de service.
	 * @return The list of services by service categories (integer = id of service category).
	 * <br> La liste des services par catégorie de service (integer = id de la catégorie de
	 * service).
	 */
	public Map<Integer, List<Service>> getServices() {
		return services;
	}

	/**
	 * Set the selected services by service categories of the report. <br> Fixe les services par
	 * catégorie de service du rapport.
	 * @param services The Map of list of services by service category id. <br> La Map des services
	 *                    par identifiant de catégorie de service.
	 */
	public void setServices(Map<Integer, List<Service>> services) {
		this.services = services;
	}

	/**
	 * Get the list of the report's contributors. <br> Renvoie la liste des intervenants du rapport.
	 * @return The list of contributors selected in the report. <br> La liste des intervenants
	 * sélectionnés dans le rapport.
	 */
	public List<Intervenant> getIntervenants() {
		return intervenants;
	}

	/**
	 * Set the list of the report's contributors. <br> Fixe la liste des intervenants du rapport.
	 * @param intervenants The list of the selected contributors in the report. <br> La liste des
	 *                        intervenants sélectionnés dans le rapport.
	 */
	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}

	/**
	 * Get the list of the report's incidents. <br> Renvoie la liste des incidents constatés du rapport.
	 * @return The list of selected incidents in the report. <br> La liste des incidents sélectionnés
	 * dans le rapport.
	 */
	public List<Incident> getIncidents() {
		return incidents;
	}

	/**
	 * Set the list of the incidents selected on the report. <br> Fixe la liste des incidents
	 * sélectionnés sur le rapport.
	 * @param incidents The list of incidents associated to the report. <br> La liste des incidents
	 *                     associés au rapport.
	 */
	public void setIncidents (List<Incident> incidents) {
		this.incidents = new ArrayList<>();
		// We add a copy of the Incident object to have different object for the different reports
		for (Incident incident : incidents)
			this.incidents.add(new Incident(incident.getId(), incident.getName(), incident.isSelected(), incident.getNbrIncident()));
	}

	/**
	 * Get the details indicated on the report. <br> Renvoie les détails indiqués sur le rapport.
	 * @return The list of the details, a map of a title by answer. <br> La liste des détails, une map
	 * d'un titre par réponse.
	 */
	public Map<String, String> getDetails() {
		return details;
	}

	/**
	 * Set the details list of the report. <br> Fixe la liste des détails du rapport.
	 * @param details The Map of detail resonses by detail title. <br> Une Map de réponses de détails
	 *                   par titre de détail.
	 */
	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	/**
	 * Get the pillar selected in the report. <br> Renvoie le pillier sélectionné dans le rapport.
	 * @return The pillar selected in the report. <br> Le pillier sélectionné dans le rapport.
	 */
	public String getPillar() {
		return pillar;
	}

	/**
	 * Set the pillar selected in the report. <br> Fixe le pillier sélectionné dans le rapport.
	 * @param pillar The pillar selected in the report. <br> Le pillier sélectionné dans le rapport.
	 */
	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	/**
	 * Get the code written in the report. <br> Renvoie le code écrit dans le rapport.
	 * @return The code written in the report. Can be empty. <br> Le code écrit dans le rapport. Peut
	 * être vide.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Set the code written in the report. <br> Fixe la valeur du code écrit dans le rapport.
	 * @param code The code in the report. <br> Le code dans le rapport.
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Get the start date of the report's action. Can't be null. <br> Renvoie la date de début de
	 * l'action du rapport. Ne peut pas être null.
	 * @return The start date in string with the format dd/mm/yy. <br> La date de début en string avec
	 * le format jj/mm/aa.
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Set the start date of the report's action. <br> Fixe la date de début de l'action du rapport.
	 * @param startDate The start date of the action. Needs to be in dd/mm/yy format. <br> La date de
	 *                     début de l'action. Doit avoir le format jj/mm/aa.
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Get the start time of the report's action. Can't be null. <br> Renvoie l'heure de ébut de
	 * l'action du rapport. Ne peut pas être null.
	 * @return The start time in string with the format hh:mm. <br> L'heure de début en string avec le
	 * format hh:mm.
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * Set the start time of the report's action. <br> Fixe l'heure de début de l'action du rapport.
	 * @param startTime The start time of the action. Needs to be in hh:mm format. <br> L'heure de
	 *                     début de l'action. Doit avoir le format hh:mm.
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * Get the end date of the report's action. <br> Renvoie la date de fin d'action du rapport.
	 * @return The action end date in string with the format dd/mm/yy. Can be empty. <br> La date de
	 * fin de l'action en string avec le format jj/mm/aa. Peut être vide.
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * Set the end date of the report's action. <br> Fixe la date de fin de l'action du rapport.
 	 * @param endDate The end date of the action. Needs to be in dd/mm/yy format. <br> La date de fin
	 *                  de l'action. Doit avoir le format jj/mm/yy.
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * Get the end time of the report's action. <br> Renvoie l'heure de fin d'action du rapport.
	 * @return The action end time in string with the format hh:mm. Can be empty. <br> L'heure de fin
	 * de l'action en string avec le format hh:mm. Peut être vide.
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * Set the end time of the report's action. <br> Fixe l'heure de fin de l'action du rapport.
	 * @param endTime The end time of the action. Needs to be in hh:mm format. <br> L'heure de fin de
	 *                   l'action. Doit avoir le format hh:mm.
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * Get the comment written in the report. <br> Renvoie le commentaire écrit dans le rapport.
	 * @return The written comment in the report. Can be empty. <br> Le commentaire écrit dans le
	 * rapport. Peut être vide.
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set the comment written in the report. <br> Fixe le commentaire écrit dans le rapport.
	 * @param comment The written comment in the report. <br> Le commentaire écrit dans le rapport.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Get the date when the user saved the report. <br> Renvoie la date à laquelle l'utilisateur a
	 * enregistré le rapport.
	 * @return The date and time when the report was saved in format dd/mm/yy - hh:mm. <br> La
	 * date et
	 * heure auxquelles le rapport a été enregistré dans le format jj/mm/aa - hh:mm.
	 */
	public String getDateSave() {
		return dateSave;
	}

	/**
	 * Get the start date and time of the action for the displaying. <br> Renvoie la date et l'heure
	 * de début de l'action pour l'affichage.
	 * @return The start date and time in format dd/mm/yy - hh:mm. <br> La date et l'heure de début
	 * dans le format jj/mm/aa - hh:mm.
	 */
	public String getDateTimeStart() {
		return startDate + " - " + startTime;
	}

	/**
	 * Get the Json Object of the report. <br> Renvoie l'objet Json correspondant au rapport.
	 * @return The JSONObject containing the report's information. <br> L'objet JSONObject contenant
	 * les informations du rapport.
	 * @throws JSONException if there is an error while writting the json object. Shouldn't occur
	 * . <br> JSONException s'il y a une erreur lors de l'écriture de l'objet json. Ne devrait pas
	 * être levée.
	 */
	public JSONObject getJSONReport() throws JSONException {
		// The JSONObject that'll be returned
		// L'objet JSONObject qui sera renvoyé
		JSONObject obj = new JSONObject();

		// Récupération des informations uniques du rapport (listes déroulantes), si l'élément
		// n'a pas de valeur alors on envoie 0 comme identifiant.
		// Get the unique information of the report (select/spinner liste). If the element hasn't
		// a value then we sent 0 as id.
		int teamId = (team != null) ? team.getId() : 0;
		int userId = (user != null) ? user.getId() : 0;
		int zoneId = (zone != null) ? zone.getId() : 0;
		int buildingId = (building != null) ? building.getId() : 0;
		int levelId = (level != null) ? level.getId() : 0;
		int cantonId = (canton != null) ? canton.getId() : 0;
		int spaceCatId = (spaceCat != null) ? spaceCat.getId() : 0;
		int spaceId = (space != null) ? space.getId() : 0;
		int frequencyId = (frequency != null) ? frequency.getId() : 0;
		int materialId = (material != null) ? material.getId() : 0;

		// Fill the json object with the id of the unique elements (there are also the input zone)
		// Remplie l'objet json avec les id des éléments uniques (il y a aussi les zones de saisies)
		obj.put("idTeam", teamId);
		obj.put("idUser", userId);
		obj.put("idZone", zoneId);
		obj.put("idBuilding", buildingId);
		obj.put("idLevel", levelId);
		obj.put("idCanton", cantonId);
		obj.put("pillar", pillar);
		obj.put("idSpaceCat", spaceCatId);
		obj.put("idSpace", spaceId);
		obj.put("idNature", nature.getId());
		obj.put("idFrequency", frequencyId);
		obj.put("idMaterial", materialId);
		obj.put("code", code);
		obj.put("startDate", startDate);
		obj.put("startTime", startTime);
		obj.put("endDate", endDate);
		obj.put("endTime", endTime);
		obj.put("comment", comment);

		// JSONArray for the list of the categories of service and their associated selected
		// services.
		// JSONArray pour la liste des catégories de service et leurs services sélectionnés
		// associés.
		JSONArray serviceCatArray = new JSONArray();

		for (Map.Entry<Integer, List<Service>> entry : services.entrySet()) {
			Integer idSCat = entry.getKey();
			List<Service> serviceList = entry.getValue();

			JSONObject serviceObj = new JSONObject();
			serviceObj.put("idServiceCat", idSCat);
			JSONArray servicesArray = new JSONArray();

			for (Service s : serviceList)
				servicesArray.put(s.getId());

			serviceObj.put("idServices", servicesArray);
			serviceCatArray.put(serviceObj);
		}

		obj.put("serviceCat", serviceCatArray);

		JSONArray participantsArray = new JSONArray();
		for (Intervenant inter : intervenants) {
			JSONObject jsonInter = new JSONObject();

			jsonInter.put("idTeam", inter.getTeam().getId());
			jsonInter.put("idParticipant", inter.getUser().getId());

			participantsArray.put(jsonInter);
		}

		obj.put("participants", participantsArray);

		JSONArray incidentsArray = new JSONArray();
		for (Incident incident : incidents) {
			JSONObject jsonIncident = new JSONObject();
			jsonIncident.put("idIncident", incident.getId());
			jsonIncident.put("nbAnomaly", incident.getNbrIncident());
			incidentsArray.put(jsonIncident);
		}

		obj.put("incidents", incidentsArray);

		JSONArray detailsArray = new JSONArray();
		for (Map.Entry<String, String> entry : details.entrySet()) {
			String title = entry.getKey();
			String answer = entry.getValue();

			JSONObject detail = new JSONObject();
			detail.put("title", title);
			detail.put("answer", answer);

			detailsArray.put(detail);
		}

		obj.put("details", detailsArray);

		return obj;
	}
}
