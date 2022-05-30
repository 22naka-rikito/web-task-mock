<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>
<link href="css/commons.css" rel="stylesheet">
<link
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
</head>
<body>
	<div id="app">
		<div class="header">
			<h1 class="site_logo">
				<a href="menu.html">商品管理システム</a>
			</h1>
			<div class="user">
				<c:if test="${not empty user}">
					<p class="user_name">${user.id}</p>
				</c:if>

				<form class="logout_form" action="servlet" method="get">
					<button class="logout_btn" type="submit" name="btn" value="logout">
						<img src="images/ドアアイコン.png">ログアウト
					</button>
				</form>
			</div>
		</div>
		<hr>

		<div class="btn">
			<c:if test="${user.role == 1}">
				<a class="basic_btn regist" href="servlet?btn=insert">新規登録</a>
			</c:if>

		</div>
		<c:if test="${not empty msg}">
			<div>
				<p>${msg}</p>
			</div>
		</c:if>
		<form method="get" action="servlet?btn=find" class="search_container">
			<input type="text" name="findWord" size="25" placeholder="キーワード検索">
			<input type="submit" name="btn" value="find">
		</form>

		<table>
			<c:if test="${not empty findSize}">
				<p>${findSize}</p>
			</c:if>
			<div class="order">
				<form action="servlet?btn=sort">
					<select class="base-text" name="select_order_by"
						onchange="submit(this.form)">
						<option value="normal">並び替え</option>
						<option value="id">商品ID</option>
						<option value="category">カテゴリ</option>
						<option value="price_asc">単価：安い順</option>
						<option value="price_desc">単価：高い順</option>
					</select>
				</form>
				
			</div>
			<thead>
				<tr>
					<th>商品ID</th>
					<th>商品名</th>
					<th>単価</th>
					<th>カテゴリ</th>
					<th>詳細</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${findList}">
					<tr>
						<td>${product.getId()}</td>
						<td>${product.getName()}</td>
						<td>${product.getPrice()}</td>
						<td>${product.getCategory().getName()}</td>
						<td><a class="detail_btn"
							href="./servlet?id=${product.getId()}&btn=detail">詳細</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<footer></footer>

	<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.js"></script>

</body>
</html>
