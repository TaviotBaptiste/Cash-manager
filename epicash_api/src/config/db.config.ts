import { Sequelize } from 'sequelize-typescript'
import { Users } from '../model/users.model';
import { Accounts } from '../model/accounts.model'
import { Bills } from '../model/bills.model';
import { Products } from '../model/products.model';
import { Assos } from '../model/assos.model';

export const connect = () => {

    const hostName = process.env.PSQL_HOST || "localhost";
    const userName = process.env.USER_DB || "postgres";
    const password = process.env.PASSWORD || "rot";
    const database = process.env.DB || "cash";
    const dialect: any = process.env.DIALECT || "postgres";

    const operatorsAliases: any = 0;

    const sequelize = new Sequelize(database, userName, password, {
        host: hostName,
        dialect,
        operatorsAliases,
        repositoryMode: true,
        pool: {
            max: 10,
            min: 0,
            acquire: 20000,
            idle: 5000
        }
    });
    
    sequelize.addModels([Users, Accounts, Bills, Products, Assos]);

    let accounts = sequelize.models.Accounts
    let users = sequelize.models.Users
    let bills = sequelize.models.Bills
    let products = sequelize.models.Products
    let assos = sequelize.models.Assos

    accounts.belongsTo(users, {
        foreignKey: 'fk_user'
    });

    bills.belongsTo(users, {
        foreignKey: 'fk_user'
    });

    assos.belongsTo(bills, {
        foreignKey: 'fk_bill'
    });

    assos.belongsTo(products, {
        foreignKey: 'fk_product'
    });



    const db: any = {};
    db.Sequelize = Sequelize;
    db.sequelize = sequelize;
    
    return db;

}