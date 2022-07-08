package org.example.interfaces;

import org.example.models.Resources;

import java.util.List;

public interface IResource {
    Resources getResource(int id);
    List<Resources> getAllResources(String accessLevel);
    boolean createResource(Resources resource);
    boolean updateResource(Resources resource);
    boolean deleteResource(int id);
}
