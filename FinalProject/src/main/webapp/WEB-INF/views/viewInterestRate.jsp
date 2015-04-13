<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	int i = 1;
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<style type="text/css">
.loading {
	background-image: url("http://cdn2.inlinkz.com/load.gif");
	background-repeat: no-repeat;
	background-position: center;
}

.cell{
	vertical-align: middle;
	display: table-cell;
}
</style>
<script type="text/javascript">
	var i = 1;

	var checkDiv = [];
	var checkSpan = [];
	var interestRate = [];
	var originalRate = []
	var period = [];
	var rowCount;
	
	function getTableData(){
		//Free all old data
		checkDiv = [];
		checkSpan = [];
		interestRate = [];
		originalRate = [];
		period = [];
		
		i = 1;
		
		rowCount = $("#rateTable tr").length;

		var form = $("#rateForm");

		while (i < rowCount) {

			checkDiv[i] = $("#checkDiv" + i);
			checkSpan[i] = $("#checkSpan" + i);

			interestRate[i] = $("#interestRate" + i);

			if(originalRate.length < rowCount){
				originalRate[i] = interestRate[i].val();
			}
			
			period[i] = $("#period" + i);

			i++;
		}
	}

	$(document).ready(function() {
		getTableData();
	});

	function onBlur(i){
		checkSpan[i].removeClass();
		checkSpan[i].text("");
		checkDiv[i].addClass("loading");
		
		setTimeout(function(){
			checkDiv[i].removeClass("loading");
			
			if(interestRate[i].val() == "" || period[i].val() == ""){
				checkSpan[i].addClass("label label-danger");
				checkSpan[i].text("Value Require");
			}
			else if(interestRate[i].val() != originalRate[i] && originalRate[i] != ""){
				checkSpan[i].addClass("label label-warning");
				checkSpan[i].text("Changing");
			} else if(originalRate[i] == ""){
				checkSpan[i].addClass("label label-success");
				checkSpan[i].text("Valid");
			} else{
				checkSpan[i].addClass("label label-success");
				checkSpan[i].text("Original");
			}
		}, 1000)
		
		return true;
	}
	
	function addRow() {
		var table = document.getElementById("rateTable");
		var row = table.insertRow($("#rateTable tr").length);
		
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		
		var j = 0;
		while(j < 4){
			if(j == 1){
				cell1.innerHTML = "<input type='text' style='border: none; width: 100px; height: 34px;' value='" + i + "' id='id" + i + "' name='id" + i + "' readonly='readonly'/>";
			} else if(j == 2){
				cell2.innerHTML = "<input type='text' class='form-control' id='interestRate" + i + "' name='interestRate" + i + "' onblur='onBlur(" + i + ")'/>";
			} else if(j == 3){
				cell3.innerHTML = "<input type='text' class='form-control' style='width: 100px;' id='period" + i + "' name='period" + i + "' onblur='onBlur(" + i + ")'/>";
			} else{
				cell4.innerHTML = "<div id='checkDiv" + i + "' style='width: 100px; height: 34px; display: table;'><span id='checkSpan" + i + "' style='display: table-cell; vertical-align: middle;'></span></div>";
			}
			
			j ++;
		}
		<%i ++;%>
		
		//Set focus
		document.getElementById("interestRate" + i).focus();
		var totalRow = $("#rowCount");
		totalRow.val(i);
		
		getTableData();
	}
</script>

</head>
<body>
<%i = 1; %>
	<form id="rateForm" action="changeRate" method="post">
		<table class="table" id="rateTable" style="width: 60%">
			<tr>
				<th>ID</th>
				<th>Interest Rate</th>
				<th>Period</th>
				<th></th>
			</tr>
			<c:forEach items="${rateList}" var="rate">
				<tr class="rateRow">
					<td><input type="text" style="border: none; width: 100px; height: 34px;" value="${rate.id }" id="id<%=i%>"
						name="id<%=i%>" readonly="readonly"/></td>
					<td><input type="text" class="form-control" value="${rate.interestRate }"
						id="interestRate<%=i%>" name="interestRate<%=i%>" onblur="onBlur(<%=i%>)" /></td>
					<td><input type="text" style="border: none; height: 34px; width: 100px;" value="${rate.period }"
						id="period<%=i%>" name="period<%=i%>" readonly="readonly"/></td>
					<td><div id="checkDiv<%=i%>" style="width: 100px; height: 34px; display: table;">
							<span id="checkSpan<%=i%>" style="display: table-cell; vertical-align: middle;"></span>
						</div></td>
				</tr>
				<%
					i++;
				%>
			</c:forEach>
		</table>
		<input type="submit" class="btn btn-primary" id="saveChangeRate" value="Save Changes" style="margin-right: 20px;"><input
			type="button" class="btn btn-info" onclick="addRow()" value="Add Rate" />
			
		<input type="text" id="rowCount" name="rowCount" style="display: none;"/>
	</form>
</body>
</html>