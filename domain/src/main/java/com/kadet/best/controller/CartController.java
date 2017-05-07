package com.kadet.best.controller;

import com.kadet.best.entity.Cart;
import com.kadet.best.entity.Product;
import com.kadet.best.entity.ShippingDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {

  @GetMapping("/carts")
  public ModelMap index(@RequestBody Cart cart,
                        @RequestParam String returnUrl,
                        ModelMap modelMap) {

    return modelMap;
  }

  @PostMapping("/carts")
  public String addToCart(Cart cart, int productId, String returnUrl) {
    /**
     Product prod = repository.Products.FirstOrDefault(p = > p.ProductId == productId);
     if (prod != null) {
     cart.AddItem(prod, 1);
     }
     return RedirectToAction("Index", new {
     returnUrl
     });**/
    return returnUrl;
  }

  @DeleteMapping("/carts")
  public String removeFromCart(Cart cart, int productId, String returnUrl) {
    return returnUrl;
    /*
        Product prod = repository.Products.FirstOrDefault(p = > p.ProductId == productId);
        if (prod != null) {
          cart.RemoveLine(prod);
        }
        return RedirectToAction("Index", new {
          returnUrl
        });
    */
  }

  public String summary(Cart cart) {
    return "partial view cart?";
  }

  public String checkout() {
    new ShippingDetails();
    return "shipping details part?";
  }

  @PostMapping("/checkout")
  public ModelMap checkout(Cart cart, ShippingDetails shippingDetails, ModelMap modelMap) {
    /**
     if (cart.Lines.Count() == 0) {
     ModelState.AddModelError("", "Извините, ваша корзина пуста!");
     }

     if (ModelState.IsValid) {
     orderProcessor.ProcessOrder(cart, shippingDetails);
     cart.Clear();
     return View("Completed");
     } else {
     return View(shippingDetails);
     }
     **/
    return modelMap;
  }

}
