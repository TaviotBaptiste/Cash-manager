
import { NotFoundException } from '../../utils/exceptions'
import { connect } from "../../config/db.config";
import { Users } from "../../model/users.model"
import { ProductsService } from './products.service';
import { BillsService } from './bills.service';
import * as crypto from "crypto"
import 'dotenv/config'


export class UsersService {

  private db: any = {};
  private usersRespository: any;
  private productsService: ProductsService;
  private billsService: BillsService

  constructor() {
    this.db = connect();
    this.productsService = new ProductsService();
    this.billsService = new BillsService();
    this.usersRespository = this.db.sequelize.getRepository(Users);
  }


  async findAll() {
    try {
      const users = await this.usersRespository.findAll();
      return users;
    } catch (err) {
        console.log(err);
        return [];
    }
  }

  async findOne(id: number) {
    try {
      const user = await this.usersRespository.findOne({
        where: {
            id: id
        }
      });
      return user;
    } catch (err) {
      console.log(err);
      return [];
    }
  }

  async findOneByEmail(email: string) {
    try {
      const user = await this.usersRespository.findOne({
        where: {
            email: email
        }
      });
      return user;
    } catch (err) {
      console.log(err);
      return [];
    }
  }

  async identify(email: string, password: string) {
    let user = await this.findOneByEmail(email)
    if(user){
      if(user.password == crypto.createHash("sha256").update(password).digest("hex"))
        return {status:true,id:user.id}
      else
        return {status:false,id:null}
    }else{
      return {status:false,id:null}
    }
  }

  async update(task: any, id: number) {
    let data = {};
    let user
    try {
        task.updateddate = new Date().toISOString();
        if (task.password)
          task.password = crypto.createHash("sha256").update(task.password).digest("hex");
        data = await this.usersRespository.update({...task}, {
            where: {
                id: id
            }
        });

        user = await this.findOne(id)
    } catch(err) {
      console.log(err);
    }
    return user;
}


  async create(user: any) {
    user.password = crypto.createHash("sha256").update(user.password).digest("hex");
    let data = {};
    try {
        user.createdate = new Date().toISOString();
        data = await this.usersRespository.create(user);
    } catch(err) {
      console.log(err);
    }
    return data;
  }

  async delete(userId: any) {
    let data = {};
    try {
        data = await this.usersRespository.destroy({
            where: {
                id: userId
            }
        });
    } catch(err) {
        console.log(err);
    }
    return data;
  }

  async paye(userId: any, body: any) {
    let data = {};
    let userdata = await this.findOne(userId)
    let card = body.card
    let responce = {status:null,data:{missingSolde:null, bills:null, user:null}}


    let total = 0

    for (let i = 0; i < card.length; i++) {
      const productId = card[i].id;
      const qty = card[i].id
      let product = await this.productsService.findOne(productId)

      total += product.prix * qty;   
    }

    if(total > userdata.solde){
      responce.status = false
      responce.data.missingSolde = total - userdata.solde
      responce.data.user = userdata
    }else{
      let ref = "REF"+Date.now()+userId
      let bills = {reference:ref,fk_user:userdata.id,total:total}
      responce.data.bills = await this.billsService.create(bills,card)

      if(bills != null){
        
        responce.data.user = await this.removeCash(userId,total)
        
        if(responce.data.user.solde == (userdata.solde - total))
          responce.status = true
        
      }
      
    }
    return responce;
  }

  async addCash(userId: any, body: any){
    let userdata = await this.findOne(userId)
    let solde = userdata.solde += body.cash

    let updateUser = await this.update({"solde":solde},userId)

    return updateUser
  }

  async removeCash(userId: any, cash: number){
    let userdata = await this.findOne(userId)
    let solde = userdata.solde -= cash
    let updateUser = await this.update({"solde":solde},userId)

    return updateUser
  }
}
