package entity;

import database.DAO.CuoreDAO;

public class Cuore {
    private Cuore(){
    }

    public static boolean removeCuore(int poesia_id, int user_id){
        return CuoreDAO.removeCuore(poesia_id, user_id);
    }

    public static boolean addCuore(int poesia_id, int user_id){
        return CuoreDAO.addCuore(poesia_id, user_id);
    }

    public static boolean hasUserLiked(int poesia_id, int user_id){
        return CuoreDAO.hasUserLiked(poesia_id, user_id);
    }

    public static int getNumCuori(int poesiaId) {
        return CuoreDAO.getNumCuori(poesiaId);
    }
}
