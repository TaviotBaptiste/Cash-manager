
import { NotFoundException } from '../../utils/exceptions'
import { connect } from "../../config/db.config";
import { Assos } from "../../model/assos.model"
import { ProductsService } from './products.service';
import 'dotenv/config'

export class AssosService {

  private db: any = {};
  private procuctsRespository: any;
  private productsService: ProductsService;

  constructor() {
    this.db = connect();
    this.productsService = new ProductsService();
    this.procuctsRespository = this.db.sequelize.getRepository(Assos);
  }


  async findAll() {
    try {
      const assos = await this.procuctsRespository.findAll();
      console.log('assos:::', assos);
      return assos;
    } catch (err) {
        console.log(err);
        return [];
    }
  }

  async findAllForBills(id: number) {
    try {
      const asso = await this.procuctsRespository.findAll({
        where: {
            fk_bill: id
        }
      });

      let newAssos = []

      for (let i = 0; i < asso.length; i++) {
        const element = asso[i];
        const product = await this.productsService.findOne(element.fk_product)

        newAssos.push(
          {
            fk_product:element.fk_product,
            qty:element.qty,
            productName:product.libelle,
            prix:product.prix,
            totalLine:product.prix*element.qty
          })
      }
      
      return newAssos;
    } catch (err) {
        console.log(err);
        return [];
    }
  }





  async create(asso: any) {
    let data = {};
    try {
        asso.createdate = new Date().toISOString();
        data = await this.procuctsRespository.create(asso);
    } catch(err) {
      console.log(err);
    }
    return data;
  }

}
