import { Router } from 'express'
import { BillsService } from '../services/bills.service'

import { BadRequestException, NotFoundException } from '../../utils/exceptions'
/**
 * Nous créeons un `Router` Express, il nous permet de créer des routes en dehors du fichier `src/index.ts`
 */
const BillsController = Router()

/**
 * Instance de notre service
 */
const billsService = new BillsService()


BillsController.get('/:UserId', async (req, res) => {
  const id = Number(req.params.UserId)
  let bills = await billsService.findAll(id)
  return res
    .status(200)
    .json(bills)
  
})

BillsController.get('/:UserId/:BillId', async (req, res) => {
  const userId = Number(req.params.UserId)
  const billId = Number(req.params.BillId)

  console.log("test")

  let bills = await billsService.findOne(billId,userId)
  return res
    .status(200)
    .json(bills)
  
})


export { BillsController }