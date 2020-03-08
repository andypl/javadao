package pl.info.czerniak.library.dao;

public abstract class DAOFactory {
    public final static int POSTGRESQL_DAO = 1;
    private static DAOFactory instance;

    public abstract BookDAO getBookDAO();
    public abstract UserDAO getUserDAO();

    public static DAOFactory getDaoFactory(int factoryType){
        if(instance == null){
            if(factoryType == POSTGRESQL_DAO){
                instance = new PostgresqlDaoFactory();
            }
        }
        return instance;
    }
}
