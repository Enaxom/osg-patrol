package com.app.osgrim.data;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public Report(Nature nature, String startDate, String startTime, String dateSave) {
		this.nature = nature;
		this.startDate = startDate;
		this.startTime = startTime;
		this.dateSave = dateSave;
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

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Canton getCanton() {
		return canton;
	}

	public void setCanton(Canton canton) {
		this.canton = canton;
	}

	public SpaceCat getSpaceCat() {
		return spaceCat;
	}

	public void setSpaceCat(SpaceCat spaceCat) {
		this.spaceCat = spaceCat;
	}

	public Space getSpace() {
		return space;
	}

	public void setSpace(Space space) {
		this.space = space;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Map<Integer, List<Service>> getServices() {
		return services;
	}

	public void setServices(Map<Integer, List<Service>> services) {
		this.services = services;
	}

	public List<Intervenant> getIntervenants() {
		return intervenants;
	}

	public void setIntervenants(List<Intervenant> intervenants) {
		this.intervenants = intervenants;
	}

	public List<Incident> getIncidents() {
		return incidents;
	}

	public void setIncidents (List<Incident> incidents) {
		this.incidents = new ArrayList<>();
		for (Incident incident : incidents)
			this.incidents.add(new Incident(incident.getId(), incident.getName(), incident.isSelected(), incident.getNbrIncident()));
	}

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
