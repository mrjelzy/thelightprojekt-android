<?php
if (!defined('_PS_VERSION_')) {
    exit;
}

class GlassesPrescription extends Module {
    // FONCTION DE CONSTRUCTION
    public function __construct() {
        $this->name = 'Glasses Prescription';
        $this->tab = 'sell';
        $this->version = '1.0.0';
        $this->author = 'The Light Projekt';
        $this->need_instance = 0;
        $this->ps_versions_compliancy = [
            'min' => '1.7',
            'max' => _PS_VERSION_
        ];

        parent::__construct();

        $this->displayName = $this->l('Glasses Prescription');
        $this->description = $this->l('Module de gestion d\'ordonnances de lunettes de vues avec correction');
        $this->confirmUninstall = $this->l('Êtes-vous sûr de vouloir désinstaller Glasses Prescription ? (la table \'prescriptions\' sera supprimée)');
    }

    // FONCTION D'INSTALLATION
    public function install() {
        return (parent::install() && $this->createPrescriptionsTable() && $this->registerHook('actionAdminControllerSetMedia') && $this->registerHook('displayAdminProductsExtra') && $this->registerApiRoutes());
    }

    // FONCTION DE DESINSTALLATION
    public function uninstall() {
        return (parent::uninstall() && $this->deletePrescriptionsTable());
    }

    // FONCTION DE CREATION DE LA TABLE 'prescriptions'
    private function createPrescriptionTable() {
        $sql = 'CREATE TABLE IF NOT EXISTS `'._DB_PREFIX_.'prescriptions` (
            `id_prescription` INT(11) NOT NULL AUTO_INCREMENT,
            `id_customer` INT(11) NOT NULL,
            `id_order` INT(11) NOT NULL,
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

    // FONCTION DE SUPPRESSION DE LA TABLE 'prescriptions'
    private function deletePrescriptionsTable() {
        $sql = "DROP TABLE IF EXISTS " . _DB_PREFIX_ . "prescriptions;";
        return Db::getInstance()->execute($sql);
    }

    // FONCTION D'INITIALISATION DES ROUTES API
    public function registerApiRoutes() {
        return [
            'create' => [
                'route' => '/api/GlassesPrescription/create',
                'controller' => 'createPrescription',
                'methods' => ['POST'],
                'params' => [],
            ],
            'read' => [
                'route' => '/api/GlassesPrescription/read',
                'controller' => 'readPrescription',
                'methods' => ['GET'],
                'params' => [],
            ],
            'update' => [
                'route' => '/api/GlassesPrescription/update',
                'controller' => 'updatePrescription',
                'methods' => ['PUT'],
                'params' => [],
            ],
            'delete' => [
                'route' => '/api/GlassesPrescription/delete',
                'controller' => 'deletePrescription',
                'methods' => ['DELETE'],
                'params' => [],
            ]
        ];
    }

    // FONCTION DE CREATION D'UNE ORDONNANCE
    public function createPrescription() {
        try {
            // Get data from request body
            $data = json_decode(file_get_contents('php://input'), true);

            // Validate and sanitize data
            // ... (add your validation and sanitization logic here)

            // Insert prescription into database
            $prescription = new Prescription();
            $prescription->id_customer = $data['id_customer'];
            $prescription->id_order = $data['id_order'];
            $prescription->sphere_left = $data['sphere_left'];
            $prescription->sphere_right = $data['sphere_right'];
            $prescription->cylinder_left = $data['cylinder_left'];
            $prescription->cylinder_right = $data['cylinder_right'];
            $prescription->axis_left = $data['axis_left'];
            $prescription->axis_right = $data['axis_right'];
            $prescription->pd_left = $data['pd_left'];
            $prescription->pd_right = $data['pd_right'];
            $prescription->add();

            // Return response
            $response = [
                'success' => true,
                'message' => 'Prescription created successfully',
                'data' => $prescription->id_prescription,
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        } catch (Exception $e) {
            // Error handling
            $response = [
                'success' => false,
                'message' => 'Failed to create prescription: ' . $e->getMessage(),
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        }
    }

    // FONCTION DE LECTURE D'UNE ORDONNANCE
    public function readPrescription() {
        try {
            // Get data from request body
            $data = json_decode(file_get_contents('php://input'), true);

            // Validate and sanitize data
            // ... (add your validation and sanitization logic here)

            // Get prescription from database
            $prescriptionId = $data['prescription_id'];
            $prescription = new Prescription($prescriptionId);

            // Return response
            $response = [
                'success' => true,
                'message' => 'Prescription retrieved successfully',
                'data' => [
                    'prescription_id' => $prescription->id_prescription,
                    'id_customer' => $prescription->id_customer,
                    'id_order' => $prescription->id_order,
                    'sphere_left' => $prescription->sphere_left,
                    'sphere_right' => $prescription->sphere_right,
                    'cylinder_left' => $prescription->cylinder_left,
                    'cylinder_right' => $prescription->cylinder_right,
                    'axis_left' => $prescription->axis_left,
                    'axis_right' => $prescription->axis_right,
                    'pd_left' => $prescription->pd_left,
                    'pd_right' => $prescription->pd_right,
                ],
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        } catch (Exception $e) {
            // Error handling
            $response = [
                'success' => false,
                'message' => 'Failed to retrieve prescription: ' . $e->getMessage(),
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        }
    }

    // FONCTION DE MODIFICATION D'UNE ORDONNANCE
    public function updatePrescription() {
        try {
            // Get data from request body
            $data = json_decode(file_get_contents('php://input'), true);

            // Validate and sanitize data
            // ... (add your validation and sanitization logic here)

            // Get existing prescription from database
            $prescriptionId = $data['prescription_id'];
            $prescription = new Prescription($prescriptionId);

            // Update prescription
            $prescription->id_customer = $data['id_customer'];
            $prescription->id_order = $data['id_order'];
            $prescription->sphere_left = $data['sphere_left'];
            $prescription->sphere_right = $data['sphere_right'];
            $prescription->cylinder_left = $data['cylinder_left'];
            $prescription->cylinder_right = $data['cylinder_right'];
            $prescription->axis_left = $data['axis_left'];
            $prescription->axis_right = $data['axis_right'];
            $prescription->pd_left = $data['pd_left'];
            $prescription->pd_right = $data['pd_right'];
            $prescription->update();

            // Return response
            $response = [
                'success' => true,
                'message' => 'Prescription updated successfully',
                'data' => $prescriptionId,
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        } catch (Exception $e) {
            // Error handling
            $response = [
                'success' => false,
                'message' => 'Failed to update prescription: ' . $e->getMessage(),
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        }
    }

    // FONCTION DE SUPPRESSION D'UNE ORDONNANCE
    public function deletePrescription() {
        try {
            // Get data from request body
            $data = json_decode(file_get_contents('php://input'), true);

            // Validate and sanitize data
            // ... (add your validation and sanitization logic here)

            // Get prescription from database
            $prescriptionId = $data['prescription_id'];
            $prescription = new Prescription($prescriptionId);

            // Delete prescription from database
            $prescription->delete();

            // Return response
            $response = [
                'success' => true,
                'message' => 'Prescription deleted successfully',
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        } catch (Exception $e) {
            // Error handling
            $response = [
                'success' => false,
                'message' => 'Failed to delete prescription: ' . $e->getMessage(),
            ];

            header('Content-Type: application/json');
            echo json_encode($response);
            exit;
        }
    }
}