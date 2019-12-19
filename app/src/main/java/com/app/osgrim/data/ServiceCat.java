package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The ServiceCat class defines a service category.
 * La classe ServiceCat (catégorie service) définit une catégorie de service.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class ServiceCat {

    private int id;
    private String name;
    private List<Service> services;

    /**
     * Creates a serviceCat. A serviceCat has an id, a name and a list of services.
     * Créé une catégorie de service. Un catégorie de service a un id, un nom et une liste de services.
     * @param id The serviceCat id. L'id de la catégorie de service.
     * @param name The serviceCat name. Le nom de la catégorie de service.
     */
    public ServiceCat(int id, String name) {
        this.id = id;
        this.name = name;
        // The services list is initialized. La liste des services est initialisée.
        this.services = new ArrayList<>();
    }

    /**
     * Permits to get the serviceCat id.
     * Permet de récupérer l'id de la catégorie de service.
     * @return The serviceCat id. L'id de la catégorie de service.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Defines a new serviceCat id.
     * Définit un nouveau id de catégorie de service.
     * @param id The serviceCat id. L'id de la catégorie de service.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Permits to get the serviceCat name.
     * Permet de récupérer le nom de la catégorie du service.
     * @return The serviceCat name. Le nom de la catégorie de service.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Defines a new serviceCat name.
     * Définit un nouveau nom de catégorie de service.
     * @param name The serviceCat name to set. Le nom de la catégorie de service à appliquer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Add a service (a Service object) to the service category.
     * Ajoute un service (un objet Service) à la catégorie de service.
     * @param service The service to add. Le service à ajouter.
     */
    public void addService(Service service) {
        // The service can't be add if it's already in the serviceCat.
        // Le service ne peut pas être ajouté si il est déjà dans la catégorie de service.
        if (!this.services.contains(service))
            this.services.add(service);
    }

    /**
     * Get all the services of the serviceCat.
     * Récupère tous les services de la catégorie de service.
     * @return The serviceCat services list. La liste des services de la catégorie de service.
     */
    public List<Service> getServices() {
        return this.services;
    }
}
