package com.ast.viewer;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.dsmviewer.utils.EclipseUtils;
import com.puppycrawl.tools.checkstyle.gui.Main;

/**
 * @author <a href="mailto:Daniil.Yaroslavtsev@gmail.com"> Daniil Yaroslavtsev</a>
 */
public class CheckstyleAstRunner implements IObjectActionDelegate {

	/** Current Eclipse Project/Package Explorer selection. Volatile as it could be set from different threads */
	private volatile IStructuredSelection selection;

	@SuppressWarnings("unused")
	private IWorkbenchPart activePart;

	private static IResource selectedElement;

	@Override
	public synchronized void setActivePart(final IAction arg0, final IWorkbenchPart activePart) {
		this.activePart = activePart;
	}

	@Override
	public synchronized void selectionChanged(final IAction action, final ISelection selectionData) {
		selection = (IStructuredSelection) selectionData;
		selectedElement = (IResource) selection.getFirstElement();
	}

	/**
	 * Runs Checkstyle AST viewer on selected Java file when "Show AST Tree" Package Explorer context menu action called
	 */
	@Override
	public synchronized void run(final IAction action) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				String javaFileAbsolutePath = EclipseUtils.getFullPath(selectedElement);
				Activator.getInstance().logToEclipseNativeLogger(Status.INFO,
						"Opening Checkstyle AST viewer for file: " + javaFileAbsolutePath);
				Main.main(new String[] { javaFileAbsolutePath });
			}
		}).start();
	}
}
