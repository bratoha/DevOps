package ru.fivt.dostavimvse;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import ru.fivt.dostavimvse.models.Client;
import ru.fivt.dostavimvse.models.Order;

/**
 * Created by akhtyamovpavel on 06.12.16.
 */
@RestController
public class OrderController {
    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String createOrder(@PathVariable("id") int clientId, @RequestParam(name = "receiver") int receiverId,
                                  @RequestBody Order order) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        try {
            Client client = session.get(Client.class, clientId);
            Client receiver = session.get(Client.class, receiverId);

            if (client == null || receiver == null) {
                JSONObject object = new JSONObject();
                object.append("message", "No such client or receiver");
                object.append("code", 400);
                return object.toString();
            }
            order.setReceiver(receiver);
            Order packedOrder = client.createOrder(order);

            Order updatedOrder = Operator.getInstance().createRoute(packedOrder);
            JSONObject response = new JSONObject();
            response.append("message", "Order created");
            response.append("code", 200);
            response.append("orderId", updatedOrder.getId());
            return response.toString();
        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.append("message", "Error happened");
            response.append("code", 400);
            return response.toString();
        }
    }

    @RequestMapping(value = "/receive", method = RequestMethod.POST)
    public String receiverOrder(@RequestParam(name="receiverId") Integer receiverId,
                                @RequestParam(name="orderId") Integer orderId) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();

        Client receiver = session.get(Client.class, receiverId);
        Order order = session.get(Order.class, orderId);
        if (receiver == null || order == null) {
            JSONObject response = new JSONObject();
            response.append("code", 400);
            response.append("message", "Невозможно получить заказ: несанкционированный вход!");
            return response.toString();
        }
        Order receivedOrder = receiver.receiveOrder(order);
        try {
            session.saveOrUpdate(receivedOrder);
            session.beginTransaction().commit();
            session.close();
            JSONObject response = new JSONObject();
            response.append("code", 200);
            response.append("message", "Заказ получен! Страница будет обновлена для отображения нового статуса!");
            return response.toString();
        } catch (Exception e) {
            JSONObject response = new JSONObject();
            response.append("code", 400);
            response.append("message", "Заказ не может быть получен! Повторите позже!");
            return response.toString();
        }
    }

}
