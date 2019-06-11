<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>上传与发布病单</title>
   </head>
  	<style type="text/css">/*结合方式2*/
		
		.largeText{
			width:120px;
			height:80px;
		}
			
	</style>
   <body>
     <form action="${pageContext.request.contextPath}/UploadHandleServlet" 
     enctype="multipart/form-data" method="post">     
     	病人性别：
     	男：<input type="radio" name="sex" value="boy">
     	女：<input type="radio" name="sex" value="girl">
     	<br/>
    	病人年龄：<input type="text" name="age"><br/>
    	主要描诉：<input type="text" class="largeText" name="chiefComplaint"><br/>
    	临床表现：<input type="text"  class="largeText" name="clincalManifestation"><br/>
    	影像表现：<input type="text"  class="largeText" name="imagingFeatures"><br/>
 		上传DICOM图像1：<input type="file" name="file1">
		上传DICOM图像2：<input type="file" name="file2"><br/>
		上传DICOM图像3：<input type="file" name="file3">
		上传DICOM图像4：<input type="file" name="file4"><br/>
		上传DICOM图像5：<input type="file" name="file5">
		上传DICOM图像6：<input type="file" name="file6"><br/>
		<br/>
		<br/>
		<br/>
		是否发布：<input type="checkbox" name="isRelease" ><br/>
		接收人id：<input type="text" name="handleEmployeeId"><br/>
		发布人id：<input type="text" name="uploadEmployeeId"><br/>
		病单优先级（1-5）：
		0<input type="radio" name="state" value="0">
		1<input type="radio" name="state" value="1">
		2<input type="radio" name="state" value="2">
		3<input type="radio" name="state" value="3">
		4<input type="radio" name="state" value="4">
         </br>
         <input type="submit" value="提交">
     </form>
   </body>
</html>