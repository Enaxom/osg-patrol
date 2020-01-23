package com.app.osgrim.data;

import java.util.ArrayList;
import java.util.List;

/**
 * The ServiceCat class defines a service category. <br>
 * La classe ServiceCat (catégorie service) définit une catégorie de service.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
public class ServiceCat extends Element {

    /**
     * The services list associated to the service category. <br>
     * La liste des services associés à la catégorie de service.
     */
    private List<Service> services;

    /**
     * Creates a serviceCat. A serviceCat has an id, a name and a list of services. <br>
     * Créé une catégorie de service. Un catégorie de service a un id, un nom et une liste de services.
     * @param id The serviceCat id. <br> L'id de la catégorie de service.
     * @param name The serviceCat name. <br> Le nom de la catégorie de service.
     */
    public ServiceCat(int id, String name) {
        super(id, name);
        // The services list is initialized. La liste des services est initialisée.
        this.services = new ArrayList<>();
    }

    /**
     * Add a service (a Service object) to the service category. <br>
     * Ajoute un service (un objet Service) à la catégorie de service.
     * @param service The service to add. <br> Le service à ajouter.
     */
    public void addService(Service service) {
        // The service can't be add if it's already in the serviceCat.
        // Le service ne peut pas être ajouté si il est déjà dans la catégorie de service.
        if (!this.services.contains(service))
            this.services.add(service);
    }

    /**
     * Get all the services of the serviceCat. <br>
     * Récupère tous les services de la catégorie de service.
     * @return The serviceCat services list. <br> La liste des services de la catégorie de service.
     */
    public List<Service> getServices() {
        return this.services;
    }
}
