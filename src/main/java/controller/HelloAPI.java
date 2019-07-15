package controller;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import entity.GameInfo;
import entity.JsonObjectHandle;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class HelloAPI extends HttpServlet {
    static {
        ObjectifyService.register(GameInfo.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        List<GameInfo> gameInfos = ofy().load().type(GameInfo.class).list();
        JsonObjectHandle jsonObjectHandle = new JsonObjectHandle();
        jsonObjectHandle.setValues(resp.getStatus(),resp.getStatus()+" 0K",gameInfos);
        resp.getWriter().println(new Gson().toJson(jsonObjectHandle));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        GameInfo gameInfo = new Gson().fromJson(content, GameInfo.class);
        Key<GameInfo> gameInfoKey = ofy().save().entity(gameInfo).now();
        resp.setStatus(HttpServletResponse.SC_CREATED);
        JsonObjectHandle jsonObjectHandle = new JsonObjectHandle();
        jsonObjectHandle.setValues(resp.getStatus(),resp.getStatus()+" CREATED",gameInfoKey);
        resp.getWriter().println(new Gson().toJson(jsonObjectHandle));

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        GameInfo updategame = new Gson().fromJson(content,GameInfo.class);
        updategame.getId();
        GameInfo existgame = ofy().load().type(GameInfo.class).id(updategame.getId()).now();
        if(existgame==null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND," Không tìm thấy game có id này");
            return;
        }
        existgame.setName(updategame.getName());
        existgame.setDescription(updategame.getDescription());
        existgame.setGenre(updategame.getGenre());
        existgame.setPublisher(updategame.getPublisher());
        existgame.setImage(updategame.getImage());
        existgame.setRelease(updategame.getRelease());
        ofy().save().entity(existgame).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        JsonObjectHandle jsonObjectHandle = new JsonObjectHandle();
        jsonObjectHandle.setValues(resp.getStatus(),resp.getStatus()+" UPDATE_OK",existgame);
        resp.getWriter().println(new Gson().toJson(jsonObjectHandle));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Long id =Long.parseLong(req.getParameter("id"));
        GameInfo existgame = ofy().load().type(GameInfo.class).id(id).now();
        if(existgame==null){
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Không tìm thấy game có id này");
            return;
        }
        existgame.setStatus(GameInfo.Status.delete);
        Key<GameInfo> gameInfoKey = ofy().save().entity(existgame).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        JsonObjectHandle jsonObjectHandle =new JsonObjectHandle();
        jsonObjectHandle.setValues(resp.getStatus(),resp.getStatus()+" DELETE_OK",gameInfoKey);
        resp.getWriter().println(new Gson().toJson(jsonObjectHandle));
    }
}
