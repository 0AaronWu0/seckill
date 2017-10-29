package org.aaron.asyntask;

/**
 * 异步任务错误信息类
 * @author as
 *
 */
public class AsynTaskErrInfo {

	private boolean succFlag;
	private boolean redoFlag;
	private String errCode;
	private String errMsg;
	private Throwable t;//异常
	
	/**
	 * 默认不需要重做
	 */
	public AsynTaskErrInfo() {
		this.succFlag = true ;
		this.redoFlag = false ;
		this.errCode = "";
		this.errMsg = "";
	}
	
	public AsynTaskErrInfo(boolean succFlag, boolean redoFlag, String errCode, String errMsg, Throwable t) {
		this.succFlag = succFlag ;
		this.redoFlag = redoFlag ;
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.t = t;
	}

	public boolean isSuccFlag() {
		return succFlag;
	}

	public void setSuccFlag(boolean succFlag) {
		this.succFlag = succFlag;
	}

	public boolean isRedoFlag() {
		return redoFlag;
	}

	public void setRedoFlag(boolean redoFlag) {
		this.redoFlag = redoFlag;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Throwable getT() {
		return t;
	}

	public void setT(Throwable t) {
		this.t = t;
	}

}
