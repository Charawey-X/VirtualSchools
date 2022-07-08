package org.example.dao;

import org.example.interfaces.IResource;
import org.example.models.Resources;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class ResourceDao implements IResource {
    private Connection connection;

    public ResourceDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Resources getResource(int id) {
        try {
            return connection.createQuery("SELECT * FROM resources WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Resources.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting resource");
        }
    }

    /**
     * @return
     */
    @Override
    public List<Resources> getAllResources(String accessLevel) {
        try {
            return connection.createQuery("SELECT * FROM resources WHERE access = :access")
                    .addParameter("access", accessLevel)
                    .executeAndFetch(Resources.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error getting all resources");
        }
    }

    /**
     * @param resource
     * @return
     */
    @Override
    public boolean createResource(Resources resource) {
        try {
            return connection.createQuery("INSERT INTO resources (name, access, description, type, url, userid) " +
                            "VALUES (:name, :access, :description, :type, :url, :userid)")
                    .bind(resource)
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error creating resource");
        }
    }

    /**
     * @param resource
     * @return
     */
    @Override
    public boolean updateResource(Resources resource) {
        try {
            return connection.createQuery("UPDATE resources SET name = :name, access = :access, created_at = :createdat, updated_at = :updatedat WHERE id = :id")
                    .bind(resource)
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error updating resource");
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteResource(int id) {
        try {
            return connection.createQuery("DELETE FROM resources WHERE id = :id")
                    .addParameter("id", id)
                    .executeUpdate().getResult() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Sql2oException("Error deleting resource");
        }
    }
}
