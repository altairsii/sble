<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title><spring:message code="label.title" /></title>

<!-- Bootstrap Core CSS -->
<link href="bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet">

<!-- DataTables CSS -->
<link
	href="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css"
	rel="stylesheet">

<!-- DataTables Responsive CSS -->
<link
	href="bower_components/datatables-responsive/css/dataTables.responsive.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<!-- 自定义样式 -->
<link href="css/mycalss.css" rel="stylesheet" type="text/css">

</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="queryword.html"><spring:message
						code="label.title" /></a>
			</div>
			<!-- /.navbar-header -->


			<!-- /.navbar-top-links -->

			<div class="navbar-default sidebar" role="navigation">
				<div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
						<li><a href="queryword.html"><i
								class="fa fa-dashboard fa-fw"></i> <spring:message
									code="label.title1" /></a></li>
					</ul>
				</div>
				<!-- /.sidebar-collapse -->
			</div>
			<!-- /.navbar-static-side -->
		</nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<spring:message code="label.title1" />
						</div>
						<div class="panel-body">
							<div class="row">
								<div class="col-lg-6">
									<form role="form" action="queryword.html" method="get">
										<div class="form-group">
											<label><spring:message code="label.word" /></label> <input
												class="form-control" name="word" value="${word }">
										</div>
										<button type="submit" class="btn btn-default">
											<spring:message code="label.search" />
										</button>
									</form>
								</div>
							</div>
							<!-- /.row (nested) -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<div class="col-lg-12">
					<h1 class="page-header">
						<spring:message code="label.wordmeaning" />
					</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables-example">
									<thead>
										<tr>
											<th><spring:message code="label.chmeaning" /></th>
											<th><spring:message code="label.enmeaning" /></th>
											<th><spring:message code="label.phoneticsymbol" /></th>
											<th><spring:message code="label.ukaudio" /></th>
											<th><spring:message code="label.usaudio" /></th>
										</tr>
									</thead>
									<tbody>
										<tr class="odd gradeX">
											<td>${qw.definition}</td>
											<td>${qw.en_definition }</td>
											<td>${qw.pronunciation }</td>
											<td><input type="image" src="img/smallspeakers.jpg"
												class="smallspeakers" onclick="playaudio('${qw.id }_uk');">
											<audio id="${qw.id}_uk" src="${qw.uk_audio }" /></td>
											<td><input type="image" src="img/smallspeakers.jpg"
												class="smallspeakers" onclick="playaudio('${qw.id }_us');">
											<audio id="${qw.id}_us" src="${qw.us_audio }"
													onclick="playaudio(this)" /></td>
										</tr>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<!-- /#wrapper -->

			<!-- jQuery -->
			<script src="bower_components/jquery/dist/jquery.min.js"></script>

			<!-- Bootstrap Core JavaScript -->
			<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

			<!-- Metis Menu Plugin JavaScript -->
			<script src="bower_components/metisMenu/dist/metisMenu.min.js"></script>

			<!-- DataTables JavaScript -->
			<script
				src="bower_components/datatables/media/js/jquery.dataTables.min.js"></script>
			<script
				src="bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>

			<!-- Custom Theme JavaScript -->
			<script src="dist/js/sb-admin-2.js"></script>

			<!-- Page-Level Demo Scripts - Tables - Use for reference -->
			<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
                responsive: true,
                "bLengthChange" : false,
                "bFilter" : false,
                "oLanguage": {
                	"sLengthMenu": "每页显示 _MENU_ 条记录",
                	"sZeroRecords": "抱歉， 没有找到",
                	"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                	"sInfoEmpty": "没有数据",
                	"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
                	"oPaginate": {
                	"sFirst": "首页",
                	"sPrevious": "前一页",
                	"sNext": "后一页",
                	"sLast": "尾页"
                	},
                	"sZeroRecords": "没有检索到数据"
                	}
        
        });
    });
    
    function playaudio(id){
    	 $("#"+id).get(0).play();
    }
    </script>
</body>

</html>
