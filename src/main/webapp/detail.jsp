<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品詳細</title>
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

	<div class="update">
		<div class="form_body">
			<div class="img_block">
				<img src="images/マッキー.png" class="product_img"><br>
			</div>
			<form action="servlet" method="get">
				<fieldset class="label-130 product_block">
					<c:if test="${not empty str}">
						<div>
							<p class="error">${str}</p>
						</div>
					</c:if>

					<div>
						<label>商品ID</label> <input type="text" name="loginId"
							value="${product.getId()}" readonly class="base-text">
					</div>
					<div>
						<label>商品名</label> <input type="text" name="userName"
							value="${product.getName()}" readonly class="base-text">
					</div>
					<div>
						<label>単価</label> <input type="text" name="tel"
							value="${product.getPrice()}" readonly class="base-text">
					</div>
					<div>
						<label>カテゴリ</label> <input type="text" name="roleName"
							value="${product.getCategory().getName()}" readonly
							class="base-text">
					</div>
					<div>
						<label>商品説明</label>
						<textarea name="description" readonly class="base-text"
							style="background-color: rgb(209, 209, 209);">
${product.getDescription()}
            			</textarea>
					</div>
				</fieldset>
				<div>
					<div class="btns">
						<input type="button" onclick="openModal()" value="削除"
							class="basic_btn"> <input type="button"
							onclick="location.href='./servlet?id=${product.getId()}&btn=edit'"
							value="編集" class="basic_btn"> <input type="button"
							onclick="location.href='./servlet?btn=back'" value="戻る"
							class="cancel_btn">
					</div>
					<div id="modal">
						<p class="modal_message">削除しますか？</p>
						<div class="btns">
							<button type="submit" class="basic_btn" name="btn" value="delete">削除</button>
							<button type="button" onclick="closeModal()" class="cancel_btn">キャンセル</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="fadeLayer"></div>
</body>
</html>
<script src="./js/commons.js"></script>