package Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Products;
import service.DetailService;
import service.IndexService;
import service.InsertService;
import service.MenuService;
import service.UpdateService;

/**
 * Servlet implementation class servlet
 */
@WebServlet("/servlet")
public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public servlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String btn = request.getParameter("btn");
		String path = "";

		HttpSession session = request.getSession(false);

		if ("find".equals(btn)) {
			// 検索
			String findWord = request.getParameter("findWord");

			MenuService manuService = new MenuService(btn);
			path = manuService.getPath();
			List<Products> list = manuService.findProducts(findWord);

			session.setAttribute("findList", list);
			session.setAttribute("findSize", "検索結果:" + list.size() + "件");
			request.setAttribute("msg", manuService.getMsg());
		} else if ("insert".equals(btn)) {
			// 登録画面に移動
			MenuService manuService = new MenuService(btn);
			path = manuService.getPath();

			request.setAttribute("category", manuService.categoryAll());
		} else if ("detail".equals(btn)) {
			// 詳細画面に移動
			String id = request.getParameter("id");
			MenuService manuService = new MenuService(btn);
			path = manuService.getPath();

			request.setAttribute("product", manuService.findProduct(Integer.parseInt(id)));
		} else if ("register".equals(btn)) {
			String productId = request.getParameter("product_id");
			String name = request.getParameter("product_name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String description = request.getParameter("description");

			InsertService insertService = new InsertService(productId, name, price, category, description);
			path = insertService.getPath(btn);

			if ("menu.jsp".equals(path)) {
				insertService.insertProduct();
				List<Products> productsList = insertService.findProducts();
				session.setAttribute("findList", productsList);
				session.setAttribute("findSize", "検索結果:" + productsList.size() + "件");
				request.setAttribute("msg", "登録が完了しました");
			} else if ("insert.jsp".equals(path)) {
				request.setAttribute("category", insertService.categoryAll());
				request.setAttribute("errorMsg", insertService.getErrorMsg());
			}
		} else if ("back".equals(btn)) {
			InsertService insertService = new InsertService();
			path = insertService.getPath(btn);
			List<Products> list = insertService.findProducts();

			session.setAttribute("findList", list);
			session.setAttribute("findSize", "検索結果:" + list.size() + "件");
		} else if ("delete".equals(btn)) {
			String productId = request.getParameter("loginId");
			String msg = "";
			DetailService detailService = new DetailService();
			path = detailService.getPath(btn, productId);

			if ("menu.jsp".equals(path)) {
				List<Products> list = detailService.delete(productId);
				msg = "削除に成功しました";
				session.setAttribute("findList", list);
				session.setAttribute("findSize", "検索結果:" + list.size() + "件");
			} else if ("detail.jsp".equals(path)) {
				msg = "削除に失敗しました。";
			}
			request.setAttribute("msg", msg);
		} else if ("edit".equals(btn)) {
			String productId = request.getParameter("id");
			DetailService detailService = new DetailService();
			path = detailService.getPath(btn, productId);
			request.setAttribute("product", detailService.findProduct(Integer.parseInt(productId)));
			request.setAttribute("category", detailService.categoryAll());
		} else if ("update".equals(btn)) {
			String productIdOld = request.getParameter("product_id_old");
			String productId = request.getParameter("product_id");
			String name = request.getParameter("product_name");
			String price = request.getParameter("price");
			String category = request.getParameter("category");
			String description = request.getParameter("description");
			UpdateService updateService = new UpdateService(productId, name, price, category, description,
					productIdOld);
			path = updateService.getPath(btn);

			if ("menu.jsp".equals(path)) {
				updateService.updateProduct();
				List<Products> productList = updateService.findProducts();

				session.setAttribute("findList", productList);
				session.setAttribute("findSize", "検索結果:" + productList.size() + "件");
				request.setAttribute("msg", "更新が完了しました");
			} else if ("update.jsp".equals(path)) {
				request.setAttribute("category", updateService.categoryAll());
				request.setAttribute("errorMsg", updateService.getErrorMsg());
				request.setAttribute("product", updateService.findProduct(Integer.parseInt(productIdOld)));
			}
		} else if ("logout".equals(btn)) {
			path = "logout.jsp";
			session = request.getSession(false);
			session.setAttribute("user", null);
		} else if ("sort".equals(btn)) {
			path = "index.jsp";
			System.out.print("a");
		} else {
			String selectOrderBy = request.getParameter("select_order_by");
			@SuppressWarnings("unchecked")
			List<Products> list = (List<Products>) session.getAttribute("findList");
			if ("id".equals(selectOrderBy)) {
				path = "menu.jsp";
				list.sort((p1, p2) -> p1.getId() >= p2.getId() ? 1 : -1);
				session.setAttribute("findList", list);
				session.setAttribute("findSize", "検索結果:" + list.size() + "件");
			} else if ("category".equals(selectOrderBy)) {
				path = "menu.jsp";
				list.sort((p1, p2) -> p1.getCategoryName().compareTo(p2.getCategoryName()));
				session.setAttribute("findList", list);
				session.setAttribute("findSize", "検索結果:" + list.size() + "件");
			} else if ("price_asc".equals(selectOrderBy)) {
				path = "menu.jsp";
				list.sort((p1, p2) -> p1.getPrice() >= p2.getPrice() ? 1 : -1);
				session.setAttribute("findList", list);
				session.setAttribute("findSize", "検索結果:" + list.size() + "件");
			} else if ("price_desc".equals(selectOrderBy)) {
				path = "menu.jsp";
				list.sort((p1, p2) -> p1.getPrice() >= p2.getPrice() ? -1 : 1);
				session.setAttribute("findList", list);
				session.setAttribute("findSize", "検索結果:" + list.size() + "件");
			}

			else {
				if (session.getAttribute("user") == null) {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} else {
					request.getRequestDispatcher("menu.jsp").forward(request, response);
				}
			}
		}
		request.getRequestDispatcher(path).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String btn = request.getParameter("btn");
		String path = "";

		if ("login".equals(btn)) {
			String id = request.getParameter("loginId");
			String pass = request.getParameter("pass");

			IndexService indexservice = new IndexService(id, pass);
			path = indexservice.getPath();

			if (path.equals("index.jsp")) {
				request.setAttribute("errorMsg", indexservice.getErrorMsg());
			} else {
				HttpSession session = request.getSession(false);
				List<Products> list = indexservice.getList();
				session.setAttribute("findList", list);
				session.setAttribute("findSize", "検索結果:" + list.size() + "件");
				session.setAttribute("user", indexservice.getUser());
			}
		}
		request.getRequestDispatcher(path).forward(request, response);
	}
}