package com.app.osgrim.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Report class defines a report.
 * La classe Report (Rapport) définie un rapport.
 */
public class Report {

	private Team team;
	private User user;
	private Zone zone;
	private Building building;
	private Level level;
	private Canton canton;
	private SpaceCat spaceCat;
	private Space space;
	private Nature nature;
	private Frequency frequency;
	private Material material;
	private Map<Integer, List<Service>> services;
	private List<Intervenant> intervenants;
	private Map<String, String> details;
	private List<Incident> incidents;
	private String pillar, code, startDate, startTime, endDate, endTime, comment, dateSave;

	/**
	 * Creates a Report object. A report must have an action nature, a start date, a start time,
	 * and a save date. Créé un objet Report. Un rapport doit avoir une nature d'action, une date
	 * de début, une heure de début et une date d'enregistrement.
	 * @param nature The action nature of the report. La nature de l'action du rapport.
	 * @param startDate The start date of the report's action. La date de début de l'action du
	 *                     rapport.
	 * @param startTime The start time of the report's action. L'heure de début de l'action du
	 *                     rapport.
	 * @param dateSave The save date of the report. La date d'enregistrement du rapport.
	 */
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
	 * Get the team chosen in the report. Renvoie l'équipe choisie dans le rapport.
	 * @return The selected team. L'équipe sélectionnée.
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * Set the selected team of the report. Fixe l'équipe sélectionnée du rapport.
	 * @param team The team selected in the report. L'équipe sélectionnée dans le rapport.
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * Get the selected user of the report. Renvoie l'utilisateur sélectionné du rapport.
	 * @return The selected user. L'utilisateur sélectionné.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Set the selected user of the report. Fixe l'utilisateur sélectionné du rapport.
	 * @param user The user selected in the report. L'utilisateur sélectionné dans le rapport.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Get the selected zone of the report. Renvoie la zone sélectionnée du rapport.
	 * @return The selected zone. La zone sélectionnée.
	 */
	public Zone getZone() {
		return zone;
	}

	/**
	 * Set the selected zone of the report. Fixe la zone sélectionnée du rapport.
	 * @param zone The zone selected in the report. La zone sélectionnée dans le rapport.
	 */
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	/**
	 * Get the selected building of the report. Renvoie le bâtiment sélectionné du rapport.
	 * @return The selected building. Le bâtiment sélectionné.
	 */
	public Building getBuilding() {
		return building;
	}

	/**
	 * Set the selected building of the report. Fixe le bâtiment sélectionné du rapport.
	 * @param building The selected building in the report. Le bâtiment sélectionné dans le rapport.
	 */
	public void setBuilding(Building building) {
		this.building = building;
	}

	/**
	 * Get the selected level of the report. Renvoie le niveau sélectionné du rapport.
	 * @return The selected level. Le niveau sélectionné.
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Set the selected level of the report. Fixe le niveau sélectionné du rapport.
	 * @param level The selected level in the report. Le niveau sélectionné dans le rapport.
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Get the selected canton of the report. Renvoie le canton sélectionné du rapport.
	 * @return The selected canton. Le canton sélectionné.
	 */
	public Canton getCanton() {
		return canton;
	}

	/**
	 * Set the selected canton of the report. Fixe le canton sélectionné du rapport.
	 * @param canton The selected canton in the report. Le canton sélectionné dans le rapport.
	 */
	public void setCanton(Canton canton) {
		this.canton = canton;
	}

	/**
	 * Get the selected space category of the report. Renvoie la catégorie de local sélectionnée du
	 * rapport.
	 * @return The selected space catégorie. La catégorie de local sélectionnée.
	 */
	public SpaceCat getSpaceCat() {
		return spaceCat;
	}

	/**
	 * Set the selected space category of the report. Fixe la catégorie de local sélectionnée du
	 * rapport.
	 * @param spaceCat The selected space category in the report. La catégorie de local
	 *                    sélectionnée dans le rapport.
	 */
	public void setSpaceCat(SpaceCat spaceCat) {
		this.spaceCat = spaceCat;
	}

	/**
	 * Get the selected space of the report. Renvoie le local sélectionné du rapport.
	 * @return The selected space in the report. Le local sélectionné dans le rapport.
	 */
	public Space getSpace() {
		return space;
	}

	/**
	 * Set the selected space of the report. Fixe le local sélectionné du rapport.
	 * @param space The selected space in the report. Le local sélectionné dans le rapport.
	 */
	public void setSpace(Space space) {
		this.space = space;
	}

	/**
	 * Get the selected nature of the report. Renvoie la nature sélectionnée du rapport.
	 * @return The selected nature in the report. La nature sélectionnée dans le rapport.
	 */
	public Nature getNature() {
		return nature;
	}

	/**
	 * Set the selected nature of the report. Fixe la nature sélectionnée du rapport.
	 * @param nature The selected nature in the report. La nature sélectionnée dans le rapport.
	 */
	public void setNature(Nature nature) {
		this.nature = nature;
	}

	/**
	 * Get the selected frequency of the report. Renvoie la périodicité sélectionnée du rapport.
	 * @return The selected frequency in the report. La périodicité sélectionnée dans le rapport.
	 */
	public Frequency getFrequency() {
		return frequency;
	}

	/**
	 * Set the selected frequency of the report. Fixe la périodicité sélectionnée du rapport.
	 * @param frequency The selected frequency in the report. La périodicité sélectionnée dans le
	 *                    rapport.
	 */
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	/**
	 * Get the selected material of the report. Renvoie le matériel sélectionné du rapport.
	 * @return The selected material in the report. Le matériel sélectionné dans le rapport.
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Set the selected material of the report. Fixe le matériel sélectionné du rapport.
	 * @param material The selected material in the report. Le matériel sélectionné dans le rapport.
	 */
	public void setMaterial(Material material) {
		this.material = material;
	}

	/**
	 * Get the list of services selected on the report by service categories. Renvoie la liste
	 * des services sélectionnés sur le rapport par catégorie de service.
	 * @return The list of services by service categories (integer = id of service categorie). La
	 * liste des services par catégorie de service (integer = id de la catégorie de service).
	 */
	public Map<Integer, List<Service>> getServices() {
		return services;
	}

	/**
	 * Set the selected services by service categories of the report. Fixe les services par
	 * catégorie de service.
	 * @param services The Map of list of services by service category id. La Map des services
	 *                    par identifiant de catégorie de service.
	 */
	public void setServices(Map<Integer, List<Service>> services) {
		this.services = services;
	}

	/**
	 * Get the list of the report's contributors. Renvoie la liste des intervenants du rapport.
	 * @return The list of contributors selected in the report. La liste des intervenants
	 * sélectionnés dans le rapport.
	 */
	public List<Intervenant> getIntervenants() {
		return intervenants;
	}

	/**
	 * Set the list of the report's contributors. Fixe la liste des intervenants du rapport.
	 * @param intervenants The list of the selected contributors in the report. La liste des
	 *                        intervenants sélectionnés dans le rapport.
	 */
	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}

	/**
	 * Get the list of the report's incidents. Renvoie la liste des incidents constatés du rapport.
	 * @return The list of selected incidents in the report. La liste des incidents sélectionnés
	 * dans le rapport.
	 */
	public List<Incident> getIncidents() {
		return incidents;
	}

	/**
	 * Set the list of the incidents selected on the report. Fixe la liste des incidents
	 * sélectionnés sur le rapport.
	 * @param incidents The list of incidents associated to the report. La liste des incidents
	 *                     associés au rapport.
	 */
	public void setIncidents (List<Incident> incidents) {
		this.incidents = new ArrayList<>();
		// We add a copy of the Incident object to have different object for the different reports
		for (Incident incident : incidents)
			this.incidents.add(new Incident(incident.getId(), incident.getName(), incident.isSelected(), incident.getNbrIncident()));
	}

	/**
	 * Get the details indicated on the report. Renvoie les détails indiqués sur le rapport.
	 * @return The list of the details, a map of a title by answer. La liste des détails, une map
	 * d'un titre par réponse.
	 */
	public Map<String, String> getDetails() {
		return details;
	}


	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

	public String getPillar() {
		return pillar;
	}

	public void setPillar(String pillar) {
		this.pillar = pillar;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDateSave() {
		return dateSave;
	}

	public String getDateTimeStart() {
		return startDate + " - " + startTime;
	}

	public JSONObject getJSONReport() throws JSONException {
		JSONObject obj = new JSONObject();

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
