package com.ast.viewer;

import java.io.File;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * 
 * @author <a href="mailto:Daniil.Yaroslavtsev@gmail.com"> Daniil Yaroslavtsev</a>
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "Checkstyle Java AST Viewer for Eclipse"; //$NON-NLS-1$

	private static ImageRegistry imageRegistry;

	private static Activator instance;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		instance = this;

		imageRegistry = new ImageRegistry();
//		loadImagesToRegistry();
	}

//	private static void loadImagesToRegistry() {
//		Bundle bundle = instance.getBundle();
//
//		Enumeration<URL> entries = bundle.findEntries("/icons/", "*.*", true);
//		while (entries.hasMoreElements()) {
//			URL url = entries.nextElement();
//			ImageDescriptor desc = ImageDescriptor.createFromURL(url);
//			imageRegistry.put(Utils.extractFileName(url), desc);
//		}
//	}

	@Override
	public void stop(BundleContext context) throws Exception {

		imageRegistry.dispose();
		imageRegistry = null;

		instance = null;

		super.stop(context);
	}

	/**
	 * Returns the current plugin instance.
	 * 
	 * @return the current shared plugin instance.
	 */
	public static Activator getInstance() {
		return instance;
	}

	public static String getPluginId() {
		return instance.getBundle().getSymbolicName();
	}

	public static void showInfoMessage(String message) {
		MessageDialog.openInformation(Display.getDefault().getActiveShell(), PLUGIN_ID, message);
	}

	public static void showErrorMessage(String message) {
		MessageDialog.openError(Display.getDefault().getActiveShell(), PLUGIN_ID, message);
	}

	public static void showErrorMessage(String message, Throwable e) {
		StringBuilder sb = new StringBuilder(message);
		sb.append(":\n");
		sb.append(Utils.extractStackTrace(e));
		MessageDialog.openError(Display.getDefault().getActiveShell(), PLUGIN_ID, sb.toString());
	}

	public static Image getImageFromRegistry(String filename) {
		return imageRegistry.get(filename);
	}

	public static ImageDescriptor getImageDescriptorFromRegistry(String imageNameOrPath) {
		String pluginId = getPluginId();
		ImageDescriptor imageDescriptor = imageDescriptorFromPlugin(pluginId, imageNameOrPath);
		if (imageDescriptor == null) {
			imageDescriptor = imageDescriptorFromPlugin(pluginId, "icons" + File.separator + imageNameOrPath);
		}
		return imageDescriptor;
	}

	public ILog getEclipseNativeLogger() {
		return getLog();
	}

	public void logToEclipseNativeLogger(int severity, String message) {
		getEclipseNativeLogger().log(new Status(severity, Activator.PLUGIN_ID, message));
	}

}
