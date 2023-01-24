import { Table, Column, DataType, Model, HasMany } from 'sequelize-typescript'

@Table
export class Assos extends Model {
  
  @Column({
    allowNull: false,
    unique: false,
    type: DataType.INTEGER,
  })
  qty: number;
  
} 

