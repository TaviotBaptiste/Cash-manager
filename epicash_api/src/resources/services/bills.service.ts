
import { NotFoundException } from '../../utils/exceptions'
import { connect } from "../../config/db.config";
import { Bills } from "../../model/bills.model"
import { AssosService } from './assos.service';
import 'dotenv/config'

export class BillsService {

  private db: any = {};
  private procuctsRespository: any;
  private assosService: AssosService;

  constructor() {
    this.db = connect();
    this.assosService = new AssosService()
    this.procuctsRespository = this.db.sequelize.getRepository(Bills);
  }


  async findAll(id) {
    try {
      const bills = await this.procuctsRespository.findAll({
        where: {
            fk_user: id
        }
      });
      let billsList = []
      for (let i = 0; i < bills.length; i++) {
        const bill = bills[i];

        let assos = await this.assosService.findAllForBills(bill.id)

        let respondBill = {id:bill.id,reference:bill.reference,line:assos,fk_user:bill.fk_user,total:bill.total,createdAt:bill.createdAt}
        
        billsList.push(respondBill)
      }
      console.log('bills:::', bills);
      return billsList;
    } catch (err) {
        console.log(err);
        return [];
    }
  }

  async findOne(id: number, userId: number) {
    try {
      const bill = await this.procuctsRespository.findOne({
        where: {
            id: id,
            fk_user:userId
        }
      });

      let assos = await this.assosService.findAllForBills(id)

      let respondBill = {id:bill.id,reference:bill.reference,line:assos,fk_user:bill.fk_user,total:bill.total,createdAt:bill.createdAt}

      return respondBill;
    } catch (err) {
        console.log(err);
        return [];
    }
  }

  async create(bill: any, card: Array<any>) {
    let data = null;
    try {
        bill.createdate = new Date().toISOString();
        data = await this.procuctsRespository.create(bill);

        if(data.id){
          for (let i = 0; i < card.length; i++) {
            const fk = card[i];

            let assos = {qty:card[i].qty,fk_product:card[i].id,fk_bill:data.id}
            await this.assosService.create(assos)
          }

          return await this.findOne(data.id,data.fk_user)
        }
    } catch(err) {
      console.log(err);
    }
    return data;
  }
}