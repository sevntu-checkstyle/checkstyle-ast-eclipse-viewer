package com.dsmviewer.utils;

import org.eclipse.core.resources.IResource;

/**
 * @author <a href="mailto:Daniil.Yaroslavtsev@gmail.com"> Daniil Yaroslavtsev</a>
 */
public final class EclipseUtils {

	private EclipseUtils() {
	}

	/**
	 * Gets the full path of the given Eclipse Project Explorer resource (Project/File/Folder, etc).
	 * 
	 * @param resource
	 *            - the resource.
	 * @return the full path of the given resource.
	 */
	public static String getFullPath(IResource resource) {
		return resource.getLocationURI().getPath().toString();
	}

}
