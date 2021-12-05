package ru.fivt.dostavimvse;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.fivt.dostavimvse.models.Client;
import ru.fivt.dostavimvse.models.Order;
import ru.fivt.dostavimvse.models.OrderStatus;
import ru.fivt.dostavimvse.models.OrderType;

/**
 * Created by akhtyamovpavel on 30.11.16.
 */
@Controller
public class MainController {


    @RequestMapping(value="/orders/{id}", method = RequestMethod.GET)
    public ModelAndView getOrderHistory(@PathVariable("id") int clientId) {
        ModelAndView modelAndView = new ModelAndView("getorderhistory");
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        Client client = session.get(Client.class, clientId);

        modelAndView.addObject("client", client);

        session.close();
        return modelAndView;
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ModelAndView getOrder(@PathVariable("id") int orderId, @RequestParam(name="clientId") int clientId) {

        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Client client = session.get(Client.class, clientId);
            if (client == null) {
                return new ModelAndView("errororder");
            }
            Order order = client.getOrder(orderId);
            if (order != null) {
                ModelAndView modelAndView = new ModelAndView("getorder");
                modelAndView.addObject("order", order);
                modelAndView.addObject("clientId", clientId);
                modelAndView.addObject("order_ready", OrderStatus.READY);
                return modelAndView;
            } else {
                return new ModelAndView("errororder");
            }
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("errororder");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/receiveOrder/{id}", method = RequestMethod.POST)
    public ModelAndView receiveOrder(@PathVariable("id") int orderId) {
        return new ModelAndView();
    }

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView getMainPage() {
        return new ModelAndView("main");
    }


    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public ModelAndView getCreateOrderPage(@PathVariable("id") int clientId) {
        ModelAndView mav = new ModelAndView("createorderpage");
        mav.addObject("clientId", clientId);
        mav.addObject("orderTypes", OrderType.values());
        return mav;
    }
}
