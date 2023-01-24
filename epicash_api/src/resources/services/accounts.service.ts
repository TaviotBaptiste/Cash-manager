
import { NotFoundException } from '../../utils/exceptions'
import { connect } from "../../config/db.config";
import { Accounts } from "../../model/accounts.model"
import { Op } from "sequelize"
import 'dotenv/config'
export class AccountsService {

  private db: any = {};
  private procuctsRespository: any;

  constructor() {
    this.db = connect();

    this.procuctsRespository = this.db.sequelize.getRepository(Accounts);
  }
  async findAllForUser(id: number) {
    try {
      const accounts = await this.procuctsRespository.findAll({
        where: {
            fk_user: id
        }
      });
      console.log('accounts:::', accounts);
      return accounts;
    } catch (err) {
        console.log(err);
        return [];
    }
  }

  async findOne(userId: number, accountId) {
    try {
      const account = await this.procuctsRespository.findOne({
        where: {
            fk_user: userId,
            id: accountId
        }
      });
      console.log('account:::', account);
      return account;
    } catch (err) {
        console.log(err);
        return [];
    }
  }





  async create(account: any) {
    let data = {};
    try {
        account.createdate = new Date().toISOString();
        data = await this.procuctsRespository.create(account);
    } catch(err) {
      console.log(err);
      return err.detail;
    }
    return data;
  }

  async delete(accountId: number, userId: number) {
    let data = {};
    try {
        data = await this.procuctsRespository.destroy({
            where: {
              [Op.and]: [
                { id: accountId },
                { fk_user: userId }
              ]
            }
        });
    } catch(err) {
        console.log(err);
    }
    return data;
  }

  async paye(accountId: number, userId: number, cvv: number) {
    return true
  }
}
