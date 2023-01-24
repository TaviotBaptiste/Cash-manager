import { Router } from 'express'
import { AccountsController } from './accounts.controller'
import { UsersService } from '../services/users.service'
import { AccountsService } from '../services/accounts.service'
import { BillsController } from './bills.controller'
import { ProductsService } from '../services/products.service'
import { BadRequestException, NotFoundException } from '../../utils/exceptions'
import test from 'node:test'
/**
 * Nous créeons un `Router` Express, il nous permet de créer des routes en dehors du fichier `src/index.ts`
 */
const UsersController = Router()

/**
 * Instance de notre service
 */
const usersService = new UsersService()
const accountsService = new AccountsService()
const productsService = new ProductsService()


UsersController.use('/accounts', AccountsController)

UsersController.use('/bills', BillsController)


UsersController.post('/paye/:id', async (req, res) => {
  const id = Number(req.params.id)
  const card = req.body

  if (!Number.isInteger(id) || !Array.isArray(card.card)) {
    return res
    .status(403)
    .json('This ID is not valid')
  }

  if (card.card.length == 0) {
    return res
    .status(403)
    .json('This data is not valid')
  }

  if (!await validCart(card.card)) {
    return res
    .status(403)
    .json('This data is not valid')
  }

  let reponce = await usersService.paye(id,card )
  return res
    .status(200)
    .json(reponce)
})



UsersController.post('/addcash/:id', async (req, res) => {
  const id = Number(req.params.id)
  const data = req.body

  if (!Number.isInteger(id)) {
    return res
    .status(403)
    .json('This ID is not valid')
  }


  if(!Number.isInteger(data.cash) || !Number.isInteger(data.account) || !Number.isInteger(data.cvv)){
    return res
    .status(403)
    .json('data not valid')
  }

  if(!await accountsService.findOne(id, data.account)){
    return res
    .status(403)
    .json('this account does not exist')
  }

  let isPay = accountsService.paye(data.account, id, data.cvv)

  if(isPay){
    let reponce = await usersService.addCash(id,data )
    return res
      .status(200)
      .json(reponce)
  }else{
    return res
    .status(403)
    .json('payment not allow')
  }

})

UsersController.get('/', async (req, res) => {
  let users = await usersService.findAll()
  return res
    .status(200)
    .json(users)
})


UsersController.get('/:id', async (req, res) => {
  const id = Number(req.params.id)

  if (!Number.isInteger(id)) {
    return res
    .status(403)
    .json('This ID is not valid')
  }

  const user = await usersService.findOne(id)

  if (!user) {
    return res
    .status(403)
    .json('Unknow user')
  }

  return res
    .status(200)
    .json(user)
})


UsersController.post('/', async (req, res) => {
  const createdUser = await usersService.create(req.body)
  return res
    .status(201)
    .json(createdUser)
})

UsersController.post('/identify', async (req, res) => {

  console.log(req.body)

  if(req.body.email == null || req.body.password == null){
    return res
    .status(403)
    .json('data not valid')
  }

  const responce = await usersService.identify(req.body.email, req.body.password)
  return res
    .status(201)
    .json(responce)
})

UsersController.put('/:id', async (req, res) => {
  const id = Number(req.params.id)

  if (!Number.isInteger(id)) {
    return res
    .status(403)
    .json('This ID is not valid')
  }

  const updatedUser = await usersService.update(req.body, id)

  return res
    .status(200)
    .json(updatedUser)
})


UsersController.delete('/:id', async (req, res) => {
  const id = Number(req.params.id)

  if (!Number.isInteger(id)) {
    return res
    .status(403)
    .json('This ID is not valid')
  }
  

  return res
    .status(200)
    .json(await usersService.delete(id))
})

export { UsersController }

async function validCart(card) {
  return new Promise(async resolve => {
    for (let index = 0; index < card.length; index++) {
      let produtId = card[index].id
      let qty = card[index].qty
      let product = await productsService.findOne(produtId)
      
      if(!produtId || !qty){
        resolve(false)
      }
      
      if (!Number.isInteger(produtId) || !Number.isInteger(qty)) {
        resolve(false)
      }
        
      if(product == null){
        resolve(false)
      }
    }
      
    resolve(true)
  })

}