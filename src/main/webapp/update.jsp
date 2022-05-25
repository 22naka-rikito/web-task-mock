<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新</title>
<link href="css/commons.css" rel="stylesheet">
</head>
<body>
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

	<div class="insert">
		<div class="form_body">
			<c:if test="${not empty errorMsg[3]}">
				<p class="error">${errorMsg[3]}</p>
			</c:if>
			<form action="servlet" method="get">
				<fieldset class="label-130">
					<div>
						<label>商品ID</label> <input type="text" name="prpduct_id_old"
							value="${product.getId()}" class="base-text" readonly>
					</div>
					<div>
						<label>商品ID</label> <input type="text" name="prpduct_id"
							value="${product.getId()}" class="base-text">
						<c:if test="${not empty errorMsg[0]}">
							<span class="error">${errorMsg[0]}</span>
						</c:if>
					</div>
					<div>
						<label>商品名</label> <input type="text" name="prpduct_name"
							value="${product.getName()}" class="base-text">
						<c:if test="${not empty errorMsg[1]}">
							<span class="error">${errorMsg[1]}</span>
						</c:if>
					</div>
					<div>
						<label>単価</label> <input type="text" name="price"
							value="${product.getPrice()}" class="base-text">
						<c:if test="${not empty errorMsg[2]}">
							<span class="error">${errorMsg[2]}</span>
						</c:if>
					</div>
					<div>
						<label>カテゴリ</label> <select name="category" class="base-text">
							<c:forEach var="category" items="${category}">
								<c:choose>
									<c:when test="${category.getId() == product.getCategoryId()}">
										<option value="${category.getId()}" selected>${category.getName()}</option>
									</c:when>
									<c:otherwise>
										<option value="${category.getId()}">${category.getName()}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
					<div>
						<label>商品説明</label>
						<textarea name="description" class="base-text">
${product.getDescription()}
            </textarea>
					</div>
					<div>
						<label>画像</label> <input type="file" name="file"> <span
							class="error">エラーメッセージ</span>
					</div>
				</fieldset>
				<div class="btns">
					<button type="button" onclick="openModal()" class="basic_btn">更新</button>
					<input type="button" onclick="location.href='./servlet?btn=back'"
						value="メニューに戻る" class="cancel_btn">
				</div>
				<div id="modal">
					<p class="modal_message">更新しますか？</p>
					<div class="btns">
						<button type="submit" class="basic_btn" name="btn" value="update">更新</button>
						<button type="button" onclick="closeModal()" class="cancel_btn">キャンセル</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="fadeLayer"></div>
</body>
</html>
<script src="./js/commons.js"></script>