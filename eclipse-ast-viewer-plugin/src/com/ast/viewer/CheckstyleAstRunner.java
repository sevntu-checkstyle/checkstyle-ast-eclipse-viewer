package com.ast.viewer;

import java.io.File;

import javax.swing.JFrame;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.puppycrawl.tools.checkstyle.gui.ParseTreeInfoPanel;

/**
 * @author <a href="mailto:Daniil.Yaroslavtsev@gmail.com"> Daniil Yaroslavtsev</a>
 */
public class CheckstyleAstRunner implements IObjectActionDelegate {

	private static final String CHECKSTYLE_AST_VIEWER_TITLE_PREFIX = "Checkstyle AST Viewer";

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

		Job job = new Job("Show Checkstyle AST tree") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				String javaFileAbsolutePath = EclipseUtils.getFullPath(selectedElement);

				String msgPrefix = "Showing Checkstyle AST tree for file: ";
				monitor.beginTask(msgPrefix + javaFileAbsolutePath, 10);

				Activator.getInstance().logToEclipseNativeLogger(Status.INFO,
						msgPrefix + javaFileAbsolutePath);

				openCheckstyleAstViewer(javaFileAbsolutePath);

				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
	}

	private static void openCheckstyleAstViewer(String javaFilePath) {
		JFrame frame = new JFrame(CHECKSTYLE_AST_VIEWER_TITLE_PREFIX);
		final ParseTreeInfoPanel panel = new ParseTreeInfoPanel();
		frame.getContentPane().add(panel);
		panel.openFile(new File(javaFilePath), frame);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}
