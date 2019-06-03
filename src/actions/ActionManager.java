package actions;

public class ActionManager // ova klasa nam sluzi za upravljanje akcijama
{
	/// pravimo propenljive
	private OpenProjectAction openProjectAction;
	private SaveProjectAction saveProjectAction;
	private SaveAsProjectAction saveAsProjectAction;
	private NewProjectAction newProjectAction;
	private DeleteProjectAction deleteProjectAction;
	private ExitProjectAction exitProjectAction;
	private SwitchProjectAction switchProjectAction;
	private ImportExcelProjectAction importExcelProjectAction;
	private ImportPDFProjectAction importPDFProjectAction;
	private ImportWordProjectAction importWordProjectAction;
	private ExportExcelProjectAction exportExcelProjectAction;
	private ExportPDFProjectAction exportPDFProjectAction;
	private ExportWordProjectAction exportWordProjectAction;
	private LoadToTreeNodeAction loadToTreeNodeAction;
	private RemoveTabProjectAction removeTabProjectAction;
	private AuthorProjectAction authorProjectAction;
	private CutProjectAction cutProjectAction;
	private CopyProjectAction copyProjectAction;
	private PasteProjectAction pasteProjectAction;
	private UndoProjectAction undoProjectAction;
	private RedoProjectAction redoProjectAction;
	private ExportProductProjectAction exportProductProjectAction;
	private BuildInstalationProjectAction buildInstalationProjectAction;

	public ActionManager() // kostruktor koji poziva metodu za kreiranje svih akcija
	{
		initialiseActions();
	}

	private void initialiseActions() // ova metoda kreira sve akcije
	{
		openProjectAction = new OpenProjectAction();
		saveProjectAction = new SaveProjectAction();
		newProjectAction = new NewProjectAction();
		deleteProjectAction = new DeleteProjectAction();
		exitProjectAction = new ExitProjectAction();
		saveAsProjectAction = new SaveAsProjectAction();
		switchProjectAction = new SwitchProjectAction();
		importExcelProjectAction = new ImportExcelProjectAction();
		importPDFProjectAction = new ImportPDFProjectAction();
		importWordProjectAction = new ImportWordProjectAction();
		exportExcelProjectAction = new ExportExcelProjectAction();
		exportPDFProjectAction = new ExportPDFProjectAction();
		exportWordProjectAction = new ExportWordProjectAction();
		loadToTreeNodeAction = new LoadToTreeNodeAction();
		removeTabProjectAction = new RemoveTabProjectAction();
		authorProjectAction = new AuthorProjectAction();
		cutProjectAction = new CutProjectAction();
		copyProjectAction = new CopyProjectAction();
		pasteProjectAction = new PasteProjectAction();
		undoProjectAction = new UndoProjectAction();
		redoProjectAction = new RedoProjectAction();
		exportProductProjectAction = new ExportProductProjectAction();
		buildInstalationProjectAction = new BuildInstalationProjectAction();
	}

	// geteri i seteri za sve akcije

	public OpenProjectAction getOpenProjectAction()
	{
		return openProjectAction;
	}

	public void setOpenProjectAction(OpenProjectAction openDiagramAction)
	{
		this.openProjectAction = openDiagramAction;
	}

	public SaveProjectAction getSaveProjectAction()
	{
		return saveProjectAction;
	}

	public void setSaveProjectAction(SaveProjectAction saveProjectAction)
	{
		this.saveProjectAction = saveProjectAction;
	}

	public NewProjectAction getNewProjectAction()
	{
		return newProjectAction;
	}

	public void setNewProjectAction(NewProjectAction newProjectAction)
	{
		this.newProjectAction = newProjectAction;
	}

	public DeleteProjectAction getDeleteProjectAction()
	{
		return deleteProjectAction;
	}

	public void setDeleteProjectAction(DeleteProjectAction deleteProjectAction)
	{
		this.deleteProjectAction = deleteProjectAction;
	}

	public ExitProjectAction getExitProjectAction()
	{
		return exitProjectAction;
	}

	public void setExitProjectAction(ExitProjectAction exitProjectAction)
	{
		this.exitProjectAction = exitProjectAction;
	}

	public SaveAsProjectAction getSaveAsProjectAction()
	{
		return saveAsProjectAction;
	}

	public void setSaveAsProjectAction(SaveAsProjectAction saveAsProjectAction)
	{
		this.saveAsProjectAction = saveAsProjectAction;
	}

	public SwitchProjectAction getSwitchProjectAction()
	{
		return switchProjectAction;
	}

	public void setSwitchProjectAction(SwitchProjectAction switchProjectAction)
	{
		this.switchProjectAction = switchProjectAction;
	}

	public ImportExcelProjectAction getImportExcelProjectAction()
	{
		return importExcelProjectAction;
	}

	public void setImportExcelProjectAction(ImportExcelProjectAction importExcelProjectAction)
	{
		this.importExcelProjectAction = importExcelProjectAction;
	}

	public ImportPDFProjectAction getImportPDFProjectAction()
	{
		return importPDFProjectAction;
	}

	public void setImportPDFProjectAction(ImportPDFProjectAction importPDFProjectAction)
	{
		this.importPDFProjectAction = importPDFProjectAction;
	}

	public ImportWordProjectAction getImportWordProjectAction()
	{
		return importWordProjectAction;
	}

	public void setImportWordProjectAction(ImportWordProjectAction importWordProjectAction)
	{
		this.importWordProjectAction = importWordProjectAction;
	}

	public ExportExcelProjectAction getExportExcelProjectAction()
	{
		return exportExcelProjectAction;
	}

	public void setExportExcelProjectAction(ExportExcelProjectAction exportExcelProjectAction)
	{
		this.exportExcelProjectAction = exportExcelProjectAction;
	}

	public ExportPDFProjectAction getExportPDFProjectAction()
	{
		return exportPDFProjectAction;
	}

	public void setExportPDFProjectAction(ExportPDFProjectAction exportPDFProjectAction)
	{
		this.exportPDFProjectAction = exportPDFProjectAction;
	}

	public ExportWordProjectAction getExportWordProjectAction()
	{
		return exportWordProjectAction;
	}

	public void setExportWordProjectAction(ExportWordProjectAction exportWordProjectAction)
	{
		this.exportWordProjectAction = exportWordProjectAction;
	}

	public LoadToTreeNodeAction getLoadToTreeNodeAction()
	{
		return loadToTreeNodeAction;
	}

	public void setLoadToTreeNodeAction(LoadToTreeNodeAction loadToTreeNodeAction)
	{
		this.loadToTreeNodeAction = loadToTreeNodeAction;
	}

	public RemoveTabProjectAction getRemoveTabProjectAction()
	{
		return removeTabProjectAction;
	}

	public void setRemoveTabProjectAction(RemoveTabProjectAction removeTabProjectAction)
	{
		this.removeTabProjectAction = removeTabProjectAction;
	}

	public AuthorProjectAction getAuthorProjectAction()
	{
		return authorProjectAction;
	}

	public void setAuthorProjectAction(AuthorProjectAction authorProjectAction)
	{
		this.authorProjectAction = authorProjectAction;
	}

	public CutProjectAction getCutProjectAction()
	{
		return cutProjectAction;
	}

	public void setCutProjectAction(CutProjectAction cutProjectAction)
	{
		this.cutProjectAction = cutProjectAction;
	}

	public CopyProjectAction getCopyProjectAction()
	{
		return copyProjectAction;
	}

	public void setCopyProjectAction(CopyProjectAction copyProjectAction)
	{
		this.copyProjectAction = copyProjectAction;
	}

	public PasteProjectAction getPasteProjectAction()
	{
		return pasteProjectAction;
	}

	public void setPasteProjectAction(PasteProjectAction pasteProjectAction)
	{
		this.pasteProjectAction = pasteProjectAction;
	}

	public UndoProjectAction getUndoProjectAction()
	{
		return undoProjectAction;
	}

	public void setUndoProjectAction(UndoProjectAction undoProjectAction)
	{
		this.undoProjectAction = undoProjectAction;
	}

	public RedoProjectAction getRedoProjectAction()
	{
		return redoProjectAction;
	}

	public void setRedoProjectAction(RedoProjectAction redoProjectAction)
	{
		this.redoProjectAction = redoProjectAction;
	}

	public ExportProductProjectAction getExportProductProjectAction()
	{
		return exportProductProjectAction;
	}

	public void setExportProductProjectAction(ExportProductProjectAction exportProductProjectAction)
	{
		this.exportProductProjectAction = exportProductProjectAction;
	}

	public BuildInstalationProjectAction getBuildInstalationProjectAction()
	{
		return buildInstalationProjectAction;
	}

	public void setBuildInstalationProjectAction(BuildInstalationProjectAction buildInstalationProjectAction)
	{
		this.buildInstalationProjectAction = buildInstalationProjectAction;
	}

}
