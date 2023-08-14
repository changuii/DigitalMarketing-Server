package dev.Store.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import dev.Store.DAO.SalesPostDAO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesPostServiceImpl implements SalesPostService{
    private final SalesPostDAO salesPostDAO;

    public SalesPostServiceImpl(@Autowired SalesPostDAO salesPostDAO) {
        this.salesPostDAO = salesPostDAO;
    }

    @Override
    public JSONObject create(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject read(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject readAll() {

        return null;
    }

    @Override
    public JSONObject update(JSONObject jsonObject) {
        return null;
    }

    @Override
    public JSONObject delete(JSONObject jsonObject) {
        return null;
    }
}
