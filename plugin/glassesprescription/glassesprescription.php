<?php

if (!defined('_PS_VERSION_')) {
    exit;
}

include_once _PS_MODULE_DIR_ . 'glassesprescription/src/Entity/Prescription.php';

class GlassesPrescription extends Module
{
    public function __construct()
    {
        $this->name = 'glassesprescription';
        $this->tab = 'front_office_features';
        $this->version = '1.0.0';
        $this->author = 'The Light Projekt';
        $this->need_instance = 0;
        $this->secure_key = Tools::encrypt($this->name);
        $this->bootstrap = true;

        parent::__construct();

        $this->displayName = 'Glasses Prescription';
    }

    public function install()
    {
        return parent::install() &&
            $this->installDB() && // Create tables in the DB
            $this->registerHook('addWebserviceResources'); // Register the module to the hook
    }

    public function uninstall() {
        return parent::uninstall() &&
            $this->uninstallDB();
    }

    public function installDB()
    {
        $sql = 'CREATE TABLE IF NOT EXISTS `'._DB_PREFIX_.'prescriptions` (
            `id_prescription` INT(11) NOT NULL AUTO_INCREMENT,
            `id_customer` INT(11) NOT NULL,
            `id_attachment` INT(11),
            `sphere_left` FLOAT(10, 2),
            `sphere_right` FLOAT(10, 2),
            `cylinder_left` FLOAT(10, 2),
            `cylinder_right` FLOAT(10, 2),
            `axis_left` FLOAT(10, 2),
            `axis_right` FLOAT(10, 2),
            `pd_left` FLOAT(10, 2),
            `pd_right` FLOAT(10, 2),
            PRIMARY KEY (`id_prescription`)
        ) ENGINE='._MYSQL_ENGINE_.' DEFAULT CHARSET=utf8;';
        return Db::getInstance()->execute($sql);
    }

    public function uninstallDB() {
        $sql = "DROP TABLE IF EXISTS " . _DB_PREFIX_ . "prescriptions;";
        return Db::getInstance()->execute($sql);
    }

    public function hookAddWebserviceResources($params)
    {
        return [
            'prescriptions' => array(
                'description' => 'Glasses prescription', // The description for those who access to this resource through WS
                'class' => 'Prescription', // The classname of your Entity
            )
        ];
    }
}